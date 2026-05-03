package com.saffron.eai.service;

import com.saffron.eai.domain.EaiRestConfig;
import com.saffron.eai.dto.EaiRestConfigDto;
import com.saffron.eai.mapper.EaiRestConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EaiRestConfigServiceImpl implements EaiRestConfigService {

    private final EaiRestConfigMapper restConfigMapper;

    @Override
    public List<EaiRestConfigDto> findAll(String interfaceId) {
        return restConfigMapper.selectRestConfigList(interfaceId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EaiRestConfigDto findById(Long id) {
        EaiRestConfig entity = restConfigMapper.selectRestConfigById(id);
        if (entity == null) throw new IllegalArgumentException("REST 설정을 찾을 수 없습니다: " + id);
        return toDto(entity);
    }

    @Override
    @Transactional
    public EaiRestConfigDto create(EaiRestConfigDto dto) {
        EaiRestConfig entity = toEntity(dto);
        restConfigMapper.insertRestConfig(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public EaiRestConfigDto update(Long id, EaiRestConfigDto dto) {
        EaiRestConfig entity = restConfigMapper.selectRestConfigById(id);
        if (entity == null) throw new IllegalArgumentException("REST 설정을 찾을 수 없습니다: " + id);

        entity.setInterfaceId(dto.getInterfaceId());
        entity.setConfigName(dto.getConfigName());
        entity.setUrl(dto.getUrl());
        entity.setHttpMethod(dto.getHttpMethod());
        entity.setTimeoutMs(dto.getTimeoutMs());
        entity.setAuthType(dto.getAuthType());
        if (dto.getAuthValue() != null && !dto.getAuthValue().isBlank()) {
            entity.setAuthValue(dto.getAuthValue());
        }
        entity.setApiKeyHeader(dto.getApiKeyHeader());
        entity.setTokenUrl(dto.getTokenUrl());
        entity.setClientId(dto.getClientId());
        if (dto.getClientSecret() != null && !dto.getClientSecret().isBlank()) {
            entity.setClientSecret(dto.getClientSecret());
        }
        entity.setTokenScope(dto.getTokenScope());
        entity.setContentType(dto.getContentType());
        entity.setRequestHeaders(dto.getRequestHeaders());
        entity.setRequestTemplate(dto.getRequestTemplate());
        entity.setSuccessHttpCodes(dto.getSuccessHttpCodes());
        entity.setResponsePath(dto.getResponsePath());
        entity.setSslVerify(dto.getSslVerify());
        entity.setProxyHost(dto.getProxyHost());
        entity.setProxyPort(dto.getProxyPort());
        entity.setProxyUser(dto.getProxyUser());
        if (dto.getProxyPassword() != null && !dto.getProxyPassword().isBlank()) {
            entity.setProxyPassword(dto.getProxyPassword());
        }
        entity.setIsActive(dto.getIsActive());

        restConfigMapper.updateRestConfig(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        restConfigMapper.deleteRestConfig(id);
    }

    private EaiRestConfigDto toDto(EaiRestConfig e) {
        EaiRestConfigDto dto = new EaiRestConfigDto();
        dto.setId(e.getId());
        dto.setInterfaceId(e.getInterfaceId());
        dto.setConfigName(e.getConfigName());
        dto.setUrl(e.getUrl());
        dto.setHttpMethod(e.getHttpMethod());
        dto.setTimeoutMs(e.getTimeoutMs());
        dto.setAuthType(e.getAuthType());
        dto.setAuthValue(null);
        dto.setApiKeyHeader(e.getApiKeyHeader());
        dto.setTokenUrl(e.getTokenUrl());
        dto.setClientId(e.getClientId());
        dto.setClientSecret(null);
        dto.setTokenScope(e.getTokenScope());
        dto.setContentType(e.getContentType());
        dto.setRequestHeaders(e.getRequestHeaders());
        dto.setRequestTemplate(e.getRequestTemplate());
        dto.setSuccessHttpCodes(e.getSuccessHttpCodes());
        dto.setResponsePath(e.getResponsePath());
        dto.setSslVerify(e.getSslVerify());
        dto.setProxyHost(e.getProxyHost());
        dto.setProxyPort(e.getProxyPort());
        dto.setProxyUser(e.getProxyUser());
        dto.setProxyPassword(null);
        dto.setIsActive(e.getIsActive());
        return dto;
    }

    private EaiRestConfig toEntity(EaiRestConfigDto dto) {
        return EaiRestConfig.builder()
                .interfaceId(dto.getInterfaceId())
                .configName(dto.getConfigName())
                .url(dto.getUrl())
                .httpMethod(dto.getHttpMethod())
                .timeoutMs(dto.getTimeoutMs())
                .authType(dto.getAuthType())
                .authValue(dto.getAuthValue())
                .apiKeyHeader(dto.getApiKeyHeader())
                .tokenUrl(dto.getTokenUrl())
                .clientId(dto.getClientId())
                .clientSecret(dto.getClientSecret())
                .tokenScope(dto.getTokenScope())
                .contentType(dto.getContentType())
                .requestHeaders(dto.getRequestHeaders())
                .requestTemplate(dto.getRequestTemplate())
                .successHttpCodes(dto.getSuccessHttpCodes())
                .responsePath(dto.getResponsePath())
                .sslVerify(dto.getSslVerify())
                .proxyHost(dto.getProxyHost())
                .proxyPort(dto.getProxyPort())
                .proxyUser(dto.getProxyUser())
                .proxyPassword(dto.getProxyPassword())
                .isActive(dto.getIsActive())
                .build();
    }
}
