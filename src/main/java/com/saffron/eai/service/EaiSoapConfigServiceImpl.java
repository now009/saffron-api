package com.saffron.eai.service;

import com.saffron.eai.domain.EaiSoapConfig;
import com.saffron.eai.dto.EaiSoapConfigDto;
import com.saffron.eai.mapper.EaiSoapConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EaiSoapConfigServiceImpl implements EaiSoapConfigService {

    private final EaiSoapConfigMapper soapConfigMapper;

    @Override
    public List<EaiSoapConfigDto> findAll(String interfaceId) {
        return soapConfigMapper.selectSoapConfigList(interfaceId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EaiSoapConfigDto findById(Long id) {
        EaiSoapConfig entity = soapConfigMapper.selectSoapConfigById(id);
        if (entity == null) throw new IllegalArgumentException("SOAP 설정을 찾을 수 없습니다: " + id);
        return toDto(entity);
    }

    @Override
    @Transactional
    public EaiSoapConfigDto create(EaiSoapConfigDto dto) {
        EaiSoapConfig entity = toEntity(dto);
        soapConfigMapper.insertSoapConfig(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public EaiSoapConfigDto update(Long id, EaiSoapConfigDto dto) {
        EaiSoapConfig entity = soapConfigMapper.selectSoapConfigById(id);
        if (entity == null) throw new IllegalArgumentException("SOAP 설정을 찾을 수 없습니다: " + id);

        entity.setInterfaceId(dto.getInterfaceId());
        entity.setConfigName(dto.getConfigName());
        entity.setWsdlUrl(dto.getWsdlUrl());
        entity.setServiceUrl(dto.getServiceUrl());
        entity.setNamespace(dto.getNamespace());
        entity.setOperationName(dto.getOperationName());
        entity.setPortName(dto.getPortName());
        entity.setSoapVersion(dto.getSoapVersion());
        entity.setSoapAction(dto.getSoapAction());
        entity.setMtomEnabled(dto.getMtomEnabled());
        entity.setWsSecurityType(dto.getWsSecurityType());
        entity.setWsUsername(dto.getWsUsername());
        if (dto.getWsPassword() != null && !dto.getWsPassword().isBlank()) {
            entity.setWsPassword(dto.getWsPassword());
        }
        entity.setWsPasswordType(dto.getWsPasswordType());
        entity.setKeystorePath(dto.getKeystorePath());
        if (dto.getKeystorePassword() != null && !dto.getKeystorePassword().isBlank()) {
            entity.setKeystorePassword(dto.getKeystorePassword());
        }
        entity.setTimeoutMs(dto.getTimeoutMs());
        entity.setRequestTemplate(dto.getRequestTemplate());
        entity.setResponsePath(dto.getResponsePath());
        entity.setIsActive(dto.getIsActive());

        soapConfigMapper.updateSoapConfig(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        soapConfigMapper.deleteSoapConfig(id);
    }

    private EaiSoapConfigDto toDto(EaiSoapConfig e) {
        EaiSoapConfigDto dto = new EaiSoapConfigDto();
        dto.setId(e.getId());
        dto.setInterfaceId(e.getInterfaceId());
        dto.setConfigName(e.getConfigName());
        dto.setWsdlUrl(e.getWsdlUrl());
        dto.setServiceUrl(e.getServiceUrl());
        dto.setNamespace(e.getNamespace());
        dto.setOperationName(e.getOperationName());
        dto.setPortName(e.getPortName());
        dto.setSoapVersion(e.getSoapVersion());
        dto.setSoapAction(e.getSoapAction());
        dto.setMtomEnabled(e.getMtomEnabled());
        dto.setWsSecurityType(e.getWsSecurityType());
        dto.setWsUsername(e.getWsUsername());
        dto.setWsPassword(null);
        dto.setWsPasswordType(e.getWsPasswordType());
        dto.setKeystorePath(e.getKeystorePath());
        dto.setKeystorePassword(null);
        dto.setTimeoutMs(e.getTimeoutMs());
        dto.setRequestTemplate(e.getRequestTemplate());
        dto.setResponsePath(e.getResponsePath());
        dto.setIsActive(e.getIsActive());
        return dto;
    }

    private EaiSoapConfig toEntity(EaiSoapConfigDto dto) {
        return EaiSoapConfig.builder()
                .interfaceId(dto.getInterfaceId())
                .configName(dto.getConfigName())
                .wsdlUrl(dto.getWsdlUrl())
                .serviceUrl(dto.getServiceUrl())
                .namespace(dto.getNamespace())
                .operationName(dto.getOperationName())
                .portName(dto.getPortName())
                .soapVersion(dto.getSoapVersion())
                .soapAction(dto.getSoapAction())
                .mtomEnabled(dto.getMtomEnabled())
                .wsSecurityType(dto.getWsSecurityType())
                .wsUsername(dto.getWsUsername())
                .wsPassword(dto.getWsPassword())
                .wsPasswordType(dto.getWsPasswordType())
                .keystorePath(dto.getKeystorePath())
                .keystorePassword(dto.getKeystorePassword())
                .timeoutMs(dto.getTimeoutMs())
                .requestTemplate(dto.getRequestTemplate())
                .responsePath(dto.getResponsePath())
                .isActive(dto.getIsActive())
                .build();
    }
}
