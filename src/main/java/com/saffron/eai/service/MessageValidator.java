package com.saffron.eai.service;

import com.saffron.eai.common.EaiMessage;

import java.util.List;

public interface MessageValidator {

    ValidationResult validate(EaiMessage message, List<String> rules);
}
