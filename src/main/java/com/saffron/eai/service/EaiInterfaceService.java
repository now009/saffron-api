package com.saffron.eai.service;

import com.saffron.eai.common.EaiResponse;
import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.domain.EaiInterfaceDef;
import com.saffron.eai.dto.request.InterfaceCreateRequest;
import com.saffron.eai.dto.response.InterfaceResponse;

import java.util.List;

public interface EaiInterfaceService {

    List<InterfaceResponse> findAll(String status, String adapterType, String keyword);

    InterfaceResponse findById(Long id);

    EaiInterfaceDef loadWithCache(String interfaceId);

    InterfaceResponse create(InterfaceCreateRequest request);

    InterfaceResponse update(Long id, InterfaceCreateRequest request);

    void toggle(Long id, Boolean isActive);

    void delete(Long id);

    EaiAdapterConfig getAdapterConfig(Long adapterId);

    EaiResponse testSend(String interfaceId, String payload);
}
