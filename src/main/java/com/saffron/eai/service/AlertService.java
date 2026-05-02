package com.saffron.eai.service;

import com.saffron.eai.common.EaiMessage;

import java.util.List;

public interface AlertService {

    void sendValidationAlert(EaiMessage message, List<String> errors);

    void sendDlqAlert(EaiMessage message);

    void sendRetryExhaustedAlert(EaiMessage message, Exception cause);
}
