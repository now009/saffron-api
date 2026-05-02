package com.saffron.eai.service;

import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.dto.request.AdapterConfigRequest;

import java.util.List;

public interface EaiAdapterService {

    List<EaiAdapterConfig> findAll(String interfaceId);

    EaiAdapterConfig findById(Long id);

    EaiAdapterConfig create(AdapterConfigRequest request);

    EaiAdapterConfig update(Long id, AdapterConfigRequest request);

    void delete(Long id);
}
