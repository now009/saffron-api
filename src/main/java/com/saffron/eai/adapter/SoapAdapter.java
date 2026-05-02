package com.saffron.eai.adapter;

import com.saffron.eai.common.EaiAdapterException;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("soapAdapter")
public class SoapAdapter extends AbstractEaiAdapter {

    @Override
    public EaiResponse send(EaiMessage message) throws EaiAdapterException {
        long startMs = System.currentTimeMillis();
        // TODO: spring-ws WebServiceTemplate으로 SOAP 메시지 전송 구현
        // WebServiceTemplate template = new WebServiceTemplate();
        // template.marshalSendAndReceive(uri, requestPayload);
        log.warn("[SOAP] 미구현 - interfaceId={}", message.getInterfaceId());
        EaiResponse response = EaiResponse.fail("SOAP 어댑터 미구현", System.currentTimeMillis() - startMs);
        saveHistory(message, response, "FAIL");
        throw new EaiAdapterException("SOAP 어댑터 미구현");
    }

    @Override
    public List<EaiMessage> receive(String interfaceId) throws EaiAdapterException {
        throw new UnsupportedOperationException("SOAP Pull 미지원");
    }

    @Override
    public HealthStatus checkHealth() {
        return HealthStatus.UNKNOWN;
    }

    @Override
    public AdapterType getType() { return AdapterType.SOAP; }
}
