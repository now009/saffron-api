package com.saffron.eai.service;

import com.saffron.eai.dto.EaiDatasourceDto;
import com.saffron.eai.dto.response.EaiApiResponse;

import java.util.List;

public interface EaiDatasourceService {

    List<EaiDatasourceDto> findAll(String dbType, Boolean isActive);

    EaiDatasourceDto findById(Long id);

    EaiDatasourceDto create(EaiDatasourceDto dto);

    EaiDatasourceDto update(Long id, EaiDatasourceDto dto);

    void delete(Long id);

    EaiApiResponse<Void> testConnection(EaiDatasourceDto dto);
}
