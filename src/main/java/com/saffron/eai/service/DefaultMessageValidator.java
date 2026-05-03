package com.saffron.eai.service;

import com.saffron.eai.common.EaiMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultMessageValidator implements MessageValidator {

    @Override
    public ValidationResult validate(EaiMessage message, List<String> rules) {
        if (rules == null || rules.isEmpty()) {
            return ValidationResult.ok();
        }

        List<String> errors = new ArrayList<>();

        for (String rule : rules) {
            if ("REQUIRE_PAYLOAD".equals(rule) &&
                    (message.getPayload() == null || message.getPayload().isBlank())) {
                errors.add("payload가 비어 있습니다.");
            }
            if ("REQUIRE_INTERFACE_ID".equals(rule) &&
                    (message.getInterfaceId() == null || message.getInterfaceId().isBlank())) {
                errors.add("interfaceId가 비어 있습니다.");
            }
        }

        return errors.isEmpty() ? ValidationResult.ok() : ValidationResult.fail(errors);
    }
}
