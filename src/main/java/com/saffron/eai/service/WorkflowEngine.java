package com.saffron.eai.service;

import com.saffron.eai.adapter.AdapterFactory;
import com.saffron.eai.adapter.EaiAdapter;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.domain.EaiInterfaceDef;
import com.saffron.eai.domain.EaiRoutingRule;
import com.saffron.eai.repository.EaiMessageHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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
    private final EaiInterfaceService interfaceService;
    private final EaiMessageHistoryRepository historyRepo;

    @Retryable(
            retryFor = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 8000)
    )
    public void execute(EaiMessage message) {
        log.info("[Workflow] START interfaceId={}", message.getInterfaceId());

        EaiInterfaceDef def = interfaceService.loadWithCache(message.getInterfaceId());
        if (!def.isActive()) {
            log.warn("[Workflow] 비활성 인터페이스 수신 무시: {}", message.getInterfaceId());
            return;
        }

        EaiMessage transformed = transformer.transform(message, def.getMappingRules());

        List<EaiAdapterConfig> targets = resolveTargets(transformed, def);

        if (def.isParallel()) {
            sendParallel(transformed, targets);
        } else {
            sendSequential(transformed, targets);
        }

        log.info("[Workflow] END interfaceId={}", message.getInterfaceId());
    }

    @Recover
    public void onMaxRetryExceeded(Exception e, EaiMessage message) {
        log.error("[Workflow] 최대 재시도 초과 interfaceId={}", message.getInterfaceId(), e);
        // TODO: DLQ 발행 및 알림 처리
    }

    private List<EaiAdapterConfig> resolveTargets(EaiMessage message, EaiInterfaceDef def) {
        List<EaiRoutingRule> rules = def.getRoutingRules();
        if (rules == null || rules.isEmpty()) {
            return List.of();
        }
        return rules.stream()
                .filter(r -> evaluateCondition(r.getConditionExpr(), message))
                .map(r -> interfaceService.getAdapterConfig(r.getTargetAdapterId()))
                .collect(Collectors.toList());
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
