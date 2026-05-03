package com.saffron.eai.service;

import com.saffron.eai.dto.EaiRestConfigDto;

import java.util.List;

public interface EaiRestConfigService {

    List<EaiRestConfigDto> findAll(String interfaceId);

    EaiRestConfigDto findById(Long id);

    EaiRestConfigDto create(EaiRestConfigDto dto);

    EaiRestConfigDto update(Long id, EaiRestConfigDto dto);

    void delete(Long id);
}
