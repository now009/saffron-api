package com.saffron.eai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.saffron.eai.common.EaiAdapterException;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.domain.EaiMappingRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageTransformer {

    private final ObjectMapper objectMapper;

    public EaiMessage transform(EaiMessage source, List<EaiMappingRule> rules) {
        if (rules == null || rules.isEmpty()) {
            return source;
        }

        try {
            JsonNode sourceNode = objectMapper.readTree(source.getPayload());
            ObjectNode targetNode = objectMapper.createObjectNode();

            for (EaiMappingRule rule : rules) {
                applyRule(sourceNode, targetNode, rule);
            }

            return EaiMessage.builder()
                    .messageId(UUID.randomUUID().toString())
                    .interfaceId(source.getInterfaceId())
                    .sourceSystem(source.getSourceSystem())
                    .targetSystem(source.getTargetSystem())
                    .direction(source.getDirection())
                    .payload(objectMapper.writeValueAsString(targetNode))
                    .endpointConfig(source.getEndpointConfig())
                    .build();

        } catch (Exception e) {
            throw new EaiAdapterException("메시지 변환 실패: " + e.getMessage(), e);
        }
    }

    private void applyRule(JsonNode source, ObjectNode target, EaiMappingRule rule) {
        String sourcePath = rule.getSourcePath();
        String targetPath = rule.getTargetPath();
        String transformType = rule.getTransformType();

        JsonNode value = resolveJsonPath(source, sourcePath);

        if (value == null || value.isMissingNode()) {
            if (rule.getDefaultValue() != null) {
                target.put(targetPath, rule.getDefaultValue());
            } else if (Boolean.TRUE.equals(rule.getIsRequired())) {
                throw new EaiAdapterException("필수 필드 누락: " + sourcePath);
            }
            return;
        }

        if ("UPPERCASE".equals(transformType)) {
            target.put(targetPath, value.asText().toUpperCase());
        } else if ("LOWERCASE".equals(transformType)) {
            target.put(targetPath, value.asText().toLowerCase());
        } else {
            target.set(targetPath, value);
        }
    }

    private JsonNode resolveJsonPath(JsonNode node, String path) {
        if (path == null || path.isBlank()) return null;
        String[] parts = path.split("\\.");
        JsonNode current = node;
        for (String part : parts) {
            if (current == null || current.isMissingNode()) return null;
            current = current.get(part);
        }
        return current;
    }
}
