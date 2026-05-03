package com.saffron.eai.service;

import com.saffron.eai.dto.EaiSoapConfigDto;

import java.util.List;

public interface EaiSoapConfigService {

    List<EaiSoapConfigDto> findAll(String interfaceId);

    EaiSoapConfigDto findById(Long id);

    EaiSoapConfigDto create(EaiSoapConfigDto dto);

    EaiSoapConfigDto update(Long id, EaiSoapConfigDto dto);

    void delete(Long id);
}
