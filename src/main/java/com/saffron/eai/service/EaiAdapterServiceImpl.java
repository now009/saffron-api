package com.saffron.eai.service;

import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.dto.request.AdapterConfigRequest;
import com.saffron.eai.mapper.EaiAdapterConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EaiAdapterServiceImpl implements EaiAdapterService {

    private final EaiAdapterConfigMapper adapterConfigMapper;

    @Override
    public List<EaiAdapterConfig> findAll(String interfaceId) {
        return adapterConfigMapper.selectAdapterConfigList(interfaceId);
    }

    @Override
    public EaiAdapterConfig findById(Long id) {
        return adapterConfigMapper.selectAdapterConfigById(id);
    }

    @Override
    public EaiAdapterConfig create(AdapterConfigRequest request) {
        EaiAdapterConfig config = toEntity(request);
        config.setCreatedAt(LocalDateTime.now());
        adapterConfigMapper.insertAdapterConfig(config);
        return config;
    }

    @Override
    public EaiAdapterConfig update(Long id, AdapterConfigRequest request) {
        EaiAdapterConfig config = toEntity(request);
        config.setId(id);
        config.setUpdatedAt(LocalDateTime.now());
        adapterConfigMapper.updateAdapterConfig(config);
        return config;
    }

    @Override
    public void delete(Long id) {
        adapterConfigMapper.deleteAdapterConfig(id);
    }

    private EaiAdapterConfig toEntity(AdapterConfigRequest req) {
        return EaiAdapterConfig.builder()
                .interfaceId(req.getInterfaceId())
                .adapterType(req.getAdapterType())
                .url(req.getUrl())
                .httpMethod(req.getHttpMethod())
                .timeoutMs(req.getTimeoutMs())
                .authType(req.getAuthType())
                .authValue(req.getAuthValue())
                .requestHeaders(req.getRequestHeaders())
                .datasourceId(req.getDatasourceId())
                .statementId(req.getStatementId())
                .operationType(req.getOperationType())
                .remoteHost(req.getRemoteHost())
                .remotePort(req.getRemotePort())
                .remoteUser(req.getRemoteUser())
                .remotePassword(req.getRemotePassword())
                .remotePath(req.getRemotePath())
                .donePath(req.getDonePath())
                .filePattern(req.getFilePattern())
                .fileEncoding(req.getFileEncoding())
                .extraConfig(req.getExtraConfig())
                .isActive(req.getIsActive())
                .build();
    }
}
