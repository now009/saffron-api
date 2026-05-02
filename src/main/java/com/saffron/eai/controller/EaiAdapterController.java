package com.saffron.eai.controller;

import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.dto.request.AdapterConfigRequest;
import com.saffron.eai.repository.EaiAdapterConfigRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/eai/adapters")
@RequiredArgsConstructor
public class EaiAdapterController {

    private final EaiAdapterConfigRepository adapterConfigRepository;

    @GetMapping
    public ResponseEntity<List<EaiAdapterConfig>> list(
            @RequestParam(required = false) String interfaceId) {
        return ResponseEntity.ok(adapterConfigRepository.selectAdapterConfigList(interfaceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EaiAdapterConfig> get(@PathVariable Long id) {
        EaiAdapterConfig config = adapterConfigRepository.selectAdapterConfigById(id);
        if (config == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(config);
    }

    @PostMapping
    public ResponseEntity<EaiAdapterConfig> create(@RequestBody @Valid AdapterConfigRequest request) {
        EaiAdapterConfig config = toEntity(request);
        config.setCreatedAt(LocalDateTime.now());
        adapterConfigRepository.insertAdapterConfig(config);
        return ResponseEntity.ok(config);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EaiAdapterConfig> update(@PathVariable Long id,
                                                    @RequestBody @Valid AdapterConfigRequest request) {
        EaiAdapterConfig config = toEntity(request);
        config.setId(id);
        config.setUpdatedAt(LocalDateTime.now());
        adapterConfigRepository.updateAdapterConfig(config);
        return ResponseEntity.ok(config);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adapterConfigRepository.deleteAdapterConfig(id);
        return ResponseEntity.ok().build();
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
