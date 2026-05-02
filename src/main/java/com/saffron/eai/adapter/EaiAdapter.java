package com.saffron.eai.adapter;

import com.saffron.eai.common.EaiAdapterException;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;

import java.util.List;

public interface EaiAdapter {

    EaiResponse send(EaiMessage message) throws EaiAdapterException;

    List<EaiMessage> receive(String interfaceId) throws EaiAdapterException;

    HealthStatus checkHealth();

    AdapterType getType();

    enum AdapterType { REST, SOAP, DB, FILE }

    enum HealthStatus { UP, DOWN, UNKNOWN }
}
