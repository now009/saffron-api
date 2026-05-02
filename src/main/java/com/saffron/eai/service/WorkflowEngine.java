package com.saffron.eai.service;

import com.saffron.eai.adapter.AdapterFactory;
import com.saffron.eai.adapter.EaiAdapter;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.TransientException;
import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.domain.EaiInterfaceDef;
import com.saffron.eai.mapper.EaiMessageHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowEngine {

    private final AdapterFactory adapterFactory;
    private final MessageTransformer transformer;
    private final MessageValidator validator;
    private final EaiInterfaceService interfaceService;
    private final EaiMessageHistoryMapper historyRepo;
    private final KafkaTemplate<String, EaiMessage> kafkaTemplate;
    private final AlertService alertService;

    @Retryable(
            retryFor = { TransientException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 8000)
    )
    public void execute(EaiMessage message) {
        log.info("[Workflow] START interfaceId={}", message.getInterfaceId());

        // 1. 인터페이스 설정 로드
        EaiInterfaceDef def = interfaceService.loadWithCache(message.getInterfaceId());
        if (!def.isActive()) {
            log.warn("[Workflow] 비활성 인터페이스 수신 무시: {}", message.getInterfaceId());
            return;
        }

        // 2. 유효성 검사
        ValidationResult validation = validator.validate(message, def.getValidationRules());
        if (!validation.isValid()) {
            kafkaTemplate.send("eai.interface.dlq", message);
            alertService.sendValidationAlert(message, validation.getErrors());
            return;
        }

        // 3. 메시지 변환
        EaiMessage transformed = transformer.transform(message, def.getMappingRules());

        // 4. 라우팅 → 어댑터 전송
        List<EaiAdapterConfig> targets = def.getRoutingRules().stream()
                .filter(r -> evaluateCondition(r.getConditionExpr(), transformed))
                .map(r -> interfaceService.getAdapterConfig(r.getTargetAdapterId()))
                .collect(Collectors.toList());

        if (def.isParallel()) {
            sendParallel(transformed, targets);
        } else {
            sendSequential(transformed, targets);
        }

        log.info("[Workflow] END interfaceId={}", message.getInterfaceId());
    }

    @Recover
    public void onMaxRetryExceeded(Exception e, EaiMessage message) {
        log.error("[Workflow] 최대 재시도 초과 → DLQ 발행: {}", message.getInterfaceId(), e);
        kafkaTemplate.send("eai.interface.dlq", message);
        alertService.sendRetryExhaustedAlert(message, e);
    }

    private void sendSequential(EaiMessage message, List<EaiAdapterConfig> targets) {
        for (EaiAdapterConfig target : targets) {
            EaiAdapter adapter = adapterFactory.getAdapter(target.getAdapterType());
            message.setEndpointConfig(target);
            adapter.send(message);
        }
    }

    private void sendParallel(EaiMessage message, List<EaiAdapterConfig> targets) {
        targets.parallelStream().forEach(target -> {
            EaiAdapter adapter = adapterFactory.getAdapter(target.getAdapterType());
            EaiMessage copy = message.copy();
            copy.setEndpointConfig(target);
            adapter.send(copy);
        });
    }

    private boolean evaluateCondition(String spEL, EaiMessage message) {
        if (spEL == null || spEL.isBlank()) return true;
        StandardEvaluationContext ctx = new StandardEvaluationContext(message);
        return Boolean.TRUE.equals(
                new SpelExpressionParser().parseExpression(spEL).getValue(ctx, Boolean.class));
    }
}
