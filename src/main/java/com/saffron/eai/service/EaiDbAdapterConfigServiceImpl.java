package com.saffron.eai.service;

import com.saffron.eai.domain.EaiDbAdapterConfig;
import com.saffron.eai.dto.EaiDbAdapterConfigDto;
import com.saffron.eai.mapper.EaiDbAdapterConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EaiDbAdapterConfigServiceImpl implements EaiDbAdapterConfigService {

    private final EaiDbAdapterConfigMapper dbAdapterConfigMapper;

    @Override
    public List<EaiDbAdapterConfigDto> findAll(String interfaceId, String datasourceId) {
        return dbAdapterConfigMapper.selectDbAdapterConfigList(interfaceId, datasourceId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EaiDbAdapterConfigDto findById(Long id) {
        EaiDbAdapterConfig entity = dbAdapterConfigMapper.selectDbAdapterConfigById(id);
        if (entity == null) throw new IllegalArgumentException("DB 어댑터 설정을 찾을 수 없습니다: " + id);
        return toDto(entity);
    }

    @Override
    @Transactional
    public EaiDbAdapterConfigDto create(EaiDbAdapterConfigDto dto) {
        EaiDbAdapterConfig entity = toEntity(dto);
        dbAdapterConfigMapper.insertDbAdapterConfig(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public EaiDbAdapterConfigDto update(Long id, EaiDbAdapterConfigDto dto) {
        EaiDbAdapterConfig entity = dbAdapterConfigMapper.selectDbAdapterConfigById(id);
        if (entity == null) throw new IllegalArgumentException("DB 어댑터 설정을 찾을 수 없습니다: " + id);

        entity.setInterfaceId(dto.getInterfaceId());
        entity.setDatasourceId(dto.getDatasourceId());
        entity.setStatementId(dto.getStatementId());
        entity.setOperationType(dto.getOperationType());
        entity.setResultType(dto.getResultType());
        entity.setParamMapping(dto.getParamMapping());
        entity.setRollbackOnError(dto.getRollbackOnError());
        entity.setIsActive(dto.getIsActive());

        dbAdapterConfigMapper.updateDbAdapterConfig(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        dbAdapterConfigMapper.deleteDbAdapterConfig(id);
    }

    private EaiDbAdapterConfigDto toDto(EaiDbAdapterConfig e) {
        EaiDbAdapterConfigDto dto = new EaiDbAdapterConfigDto();
        dto.setId(e.getId());
        dto.setInterfaceId(e.getInterfaceId());
        dto.setDatasourceId(e.getDatasourceId());
        dto.setStatementId(e.getStatementId());
        dto.setOperationType(e.getOperationType());
        dto.setResultType(e.getResultType());
        dto.setParamMapping(e.getParamMapping());
        dto.setRollbackOnError(e.getRollbackOnError());
        dto.setIsActive(e.getIsActive());
        return dto;
    }

    private EaiDbAdapterConfig toEntity(EaiDbAdapterConfigDto dto) {
        return EaiDbAdapterConfig.builder()
                .interfaceId(dto.getInterfaceId())
                .datasourceId(dto.getDatasourceId())
                .statementId(dto.getStatementId())
                .operationType(dto.getOperationType())
                .resultType(dto.getResultType())
                .paramMapping(dto.getParamMapping())
                .rollbackOnError(dto.getRollbackOnError())
                .isActive(dto.getIsActive())
                .build();
    }
}
