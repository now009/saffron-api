package com.saffron.eai.service;

import com.saffron.eai.dto.EaiDbAdapterDefDto;

import java.util.List;

public interface EaiDbAdapterDefService {

    List<EaiDbAdapterDefDto> findAll(String dbType, String direction);

    EaiDbAdapterDefDto findById(Long id);

    EaiDbAdapterDefDto create(EaiDbAdapterDefDto dto);

    EaiDbAdapterDefDto update(Long id, EaiDbAdapterDefDto dto);

    void delete(Long id);
}
