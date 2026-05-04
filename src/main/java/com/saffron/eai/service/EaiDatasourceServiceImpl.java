package com.saffron.eai.service;

import com.saffron.eai.domain.EaiDatasource;
import com.saffron.eai.dto.EaiDatasourceDto;
import com.saffron.eai.dto.response.ConnectionTestResponse;
import com.saffron.eai.mapper.EaiDatasourceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EaiDatasourceServiceImpl implements EaiDatasourceService {

    private final EaiDatasourceMapper datasourceMapper;

    @Override
    public List<EaiDatasourceDto> findAll(String dbType, Boolean isActive) {
        return datasourceMapper.selectDatasourceList(dbType, isActive)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EaiDatasourceDto findById(Long id) {
        EaiDatasource entity = datasourceMapper.selectDatasourceById(id);
        if (entity == null) throw new IllegalArgumentException("DataSource를 찾을 수 없습니다: " + id);
        return toDto(entity);
    }

    @Override
    @Transactional
    public EaiDatasourceDto create(EaiDatasourceDto dto) {
        EaiDatasource entity = toEntity(dto);
        datasourceMapper.insertDatasource(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public EaiDatasourceDto update(Long id, EaiDatasourceDto dto) {
        EaiDatasource entity = datasourceMapper.selectDatasourceById(id);
        if (entity == null) throw new IllegalArgumentException("DataSource를 찾을 수 없습니다: " + id);

        entity.setDatasourceId(dto.getDatasourceId());
        entity.setDatasourceName(dto.getDatasourceName());
        entity.setDbType(dto.getDbType());
        entity.setJdbcUrl(dto.getJdbcUrl());
        entity.setDbUsername(dto.getDbUsername());
        if (dto.getDbPassword() != null && !dto.getDbPassword().isBlank()) {
            entity.setDbPassword(dto.getDbPassword());
        }
        entity.setDriverClass(dto.getDriverClass());
        entity.setPoolMinSize(dto.getPoolMinSize());
        entity.setPoolMaxSize(dto.getPoolMaxSize());
        entity.setPoolTimeoutMs(dto.getPoolTimeoutMs());
        entity.setQueryTimeoutSec(dto.getQueryTimeoutSec());
        entity.setDefaultSchema(dto.getDefaultSchema());
        entity.setConnectionProps(dto.getConnectionProps());
        entity.setIsActive(dto.getIsActive());
        entity.setDescription(dto.getDescription());

        datasourceMapper.updateDatasource(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        datasourceMapper.deleteDatasource(id);
    }

    @Override
    public ConnectionTestResponse testConnection(EaiDatasourceDto dto) {
        if (dto.getJdbcUrl() == null || dto.getJdbcUrl().isBlank()) {
            return ConnectionTestResponse.fail("JDBC URL이 비어있습니다.");
        }
        if (dto.getDriverClass() == null || dto.getDriverClass().isBlank()) {
            return ConnectionTestResponse.fail("Driver Class가 비어있습니다.");
        }

        String password = dto.getDbPassword();
        if ((password == null || password.isBlank()) && dto.getId() != null) {
            EaiDatasource saved = datasourceMapper.selectDatasourceById(dto.getId());
            if (saved != null) password = saved.getDbPassword();
        }

        try {
            Class.forName(dto.getDriverClass());
        } catch (ClassNotFoundException e) {
            return ConnectionTestResponse.fail("Driver Class를 찾을 수 없습니다: " + dto.getDriverClass());
        }

        Properties props = new Properties();
        if (dto.getDbUsername() != null) props.setProperty("user", dto.getDbUsername());
        if (password != null)            props.setProperty("password", password);

        int loginTimeoutSec = (dto.getPoolTimeoutMs() != null && dto.getPoolTimeoutMs() > 0)
                ? Math.max(1, dto.getPoolTimeoutMs() / 1000) : 10;

        int prevTimeout = DriverManager.getLoginTimeout();
        DriverManager.setLoginTimeout(loginTimeoutSec);
        try (Connection conn = DriverManager.getConnection(dto.getJdbcUrl(), props)) {
            int validateTimeoutSec = dto.getQueryTimeoutSec() != null ? dto.getQueryTimeoutSec() : 5;
            boolean valid = conn.isValid(validateTimeoutSec);
            return valid
                    ? ConnectionTestResponse.ok("Connection 성공")
                    : ConnectionTestResponse.fail("Connection은 열렸으나 isValid 검증에 실패했습니다.");
        } catch (SQLException e) {
            log.warn("[EAI] Datasource Connection Test 실패: {}", e.getMessage());
            return ConnectionTestResponse.fail(e.getMessage());
        } finally {
            DriverManager.setLoginTimeout(prevTimeout);
        }
    }

    private EaiDatasourceDto toDto(EaiDatasource e) {
        EaiDatasourceDto dto = new EaiDatasourceDto();
        dto.setId(e.getId());
        dto.setDatasourceId(e.getDatasourceId());
        dto.setDatasourceName(e.getDatasourceName());
        dto.setDbType(e.getDbType());
        dto.setJdbcUrl(e.getJdbcUrl());
        dto.setDbUsername(e.getDbUsername());
        dto.setDbPassword(null);
        dto.setDriverClass(e.getDriverClass());
        dto.setPoolMinSize(e.getPoolMinSize());
        dto.setPoolMaxSize(e.getPoolMaxSize());
        dto.setPoolTimeoutMs(e.getPoolTimeoutMs());
        dto.setQueryTimeoutSec(e.getQueryTimeoutSec());
        dto.setDefaultSchema(e.getDefaultSchema());
        dto.setConnectionProps(e.getConnectionProps());
        dto.setIsActive(e.getIsActive());
        dto.setDescription(e.getDescription());
        dto.setCreatedBy(e.getCreatedBy());
        return dto;
    }

    private EaiDatasource toEntity(EaiDatasourceDto dto) {
        return EaiDatasource.builder()
                .datasourceId(dto.getDatasourceId())
                .datasourceName(dto.getDatasourceName())
                .dbType(dto.getDbType())
                .jdbcUrl(dto.getJdbcUrl())
                .dbUsername(dto.getDbUsername())
                .dbPassword(dto.getDbPassword())
                .driverClass(dto.getDriverClass())
                .poolMinSize(dto.getPoolMinSize())
                .poolMaxSize(dto.getPoolMaxSize())
                .poolTimeoutMs(dto.getPoolTimeoutMs())
                .queryTimeoutSec(dto.getQueryTimeoutSec())
                .defaultSchema(dto.getDefaultSchema())
                .connectionProps(dto.getConnectionProps())
                .isActive(dto.getIsActive())
                .description(dto.getDescription())
                .createdBy(dto.getCreatedBy())
                .build();
    }
}
