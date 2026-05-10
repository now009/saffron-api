package com.saffron.eai.kafka;

import com.saffron.eai.common.EaiMessage;

public interface EaiMessagePublisher {
    void publishRequest(EaiMessage message);
    void publishResponse(EaiMessage message);
    void publishDlq(EaiMessage message);
}
