package com.saffron.eai.service;

import com.saffron.eai.dto.EaiDbAdapterConfigDto;

import java.util.List;

public interface EaiDbAdapterConfigService {

    List<EaiDbAdapterConfigDto> findAll(String interfaceId, String datasourceId);

    EaiDbAdapterConfigDto findById(Long id);

    EaiDbAdapterConfigDto create(EaiDbAdapterConfigDto dto);

    EaiDbAdapterConfigDto update(Long id, EaiDbAdapterConfigDto dto);

    void delete(Long id);
}
