package com.saffron.eai.service;

import com.saffron.eai.adapter.AdapterFactory;
import com.saffron.eai.adapter.EaiAdapter;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;
import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.domain.EaiInterfaceDef;
import com.saffron.eai.dto.request.InterfaceCreateRequest;
import com.saffron.eai.dto.response.InterfaceResponse;
import com.saffron.eai.mapper.EaiAdapterConfigMapper;
import com.saffron.eai.mapper.EaiInterfaceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EaiInterfaceServiceImpl implements EaiInterfaceService {

    private final EaiInterfaceMapper interfaceRepository;
    private final EaiAdapterConfigMapper adapterConfigRepository;
    private final AdapterFactory adapterFactory;

    @Override
    public List<InterfaceResponse> findAll(String status, String adapterType, String keyword) {
        return interfaceRepository.selectInterfaceList(status, adapterType, keyword)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InterfaceResponse findById(Long id) {
        EaiInterfaceDef def = interfaceRepository.selectInterfaceById(id);
        if (def == null) {
            throw new IllegalArgumentException("인터페이스를 찾을 수 없습니다: " + id);
        }
        return toResponse(def);
    }

    @Override
    public EaiInterfaceDef loadWithCache(String interfaceId) {
        // TODO: Redis 캐시 적용 (TTL 5분)
        EaiInterfaceDef def = interfaceRepository.selectInterfaceByInterfaceId(interfaceId);
        if (def == null) {
            throw new IllegalArgumentException("인터페이스를 찾을 수 없습니다: " + interfaceId);
        }
        def.setMappingRules(interfaceRepository.selectMappingRules(interfaceId));
        def.setRoutingRules(interfaceRepository.selectRoutingRules(interfaceId));
        return def;
    }

    @Override
    @Transactional
    public InterfaceResponse create(InterfaceCreateRequest request) {
        EaiInterfaceDef def = EaiInterfaceDef.builder()
                .interfaceId(request.getInterfaceId())
                .name(request.getName())
                .sourceSystem(request.getSourceSystem())
                .targetSystem(request.getTargetSystem())
                .adapterType(request.getAdapterType())
                .direction(request.getDirection())
                .isParallel(request.getIsParallel() != null ? request.getIsParallel() : false)
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .description(request.getDescription())
                .tags(request.getTags())
                .createdAt(LocalDateTime.now())
                .build();
        interfaceRepository.insertInterface(def);
        return toResponse(def);
    }

    @Override
    @Transactional
    public InterfaceResponse update(Long id, InterfaceCreateRequest request) {
        EaiInterfaceDef def = interfaceRepository.selectInterfaceById(id);
        if (def == null) {
            throw new IllegalArgumentException("인터페이스를 찾을 수 없습니다: " + id);
        }
        def.setName(request.getName());
        def.setSourceSystem(request.getSourceSystem());
        def.setTargetSystem(request.getTargetSystem());
        def.setAdapterType(request.getAdapterType());
        def.setDirection(request.getDirection());
        def.setIsParallel(request.getIsParallel());
        def.setIsActive(request.getIsActive());
        def.setDescription(request.getDescription());
        def.setTags(request.getTags());
        def.setUpdatedAt(LocalDateTime.now());
        interfaceRepository.updateInterface(def);
        return toResponse(def);
    }

    @Override
    @Transactional
    public void toggle(Long id, Boolean isActive) {
        interfaceRepository.updateInterfaceActive(id, isActive);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        interfaceRepository.deleteInterface(id);
    }

    @Override
    public EaiAdapterConfig getAdapterConfig(Long adapterId) {
        return interfaceRepository.selectAdapterConfigById(adapterId);
    }

    @Override
    public EaiResponse testSend(String interfaceId, String payload) {
        EaiInterfaceDef def = loadWithCache(interfaceId);
        EaiAdapterConfig config = adapterConfigRepository.selectAdapterConfigList(interfaceId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("어댑터 설정 없음: " + interfaceId));

        EaiMessage message = EaiMessage.builder()
                .messageId(UUID.randomUUID().toString())
                .interfaceId(interfaceId)
                .sourceSystem(def.getSourceSystem())
                .targetSystem(def.getTargetSystem())
                .direction("SEND")
                .payload(payload)
                .endpointConfig(config)
                .build();

        EaiAdapter adapter = adapterFactory.getAdapter(config.getAdapterType());
        return adapter.send(message);
    }

    private InterfaceResponse toResponse(EaiInterfaceDef def) {
        InterfaceResponse res = new InterfaceResponse();
        res.setId(def.getId());
        res.setInterfaceId(def.getInterfaceId());
        res.setName(def.getName());
        res.setSourceSystem(def.getSourceSystem());
        res.setTargetSystem(def.getTargetSystem());
        res.setAdapterType(def.getAdapterType());
        res.setDirection(def.getDirection());
        res.setIsParallel(def.getIsParallel());
        res.setIsActive(def.getIsActive());
        res.setDescription(def.getDescription());
        res.setTags(def.getTags());
        res.setCreatedBy(def.getCreatedBy());
        res.setCreatedAt(def.getCreatedAt());
        res.setUpdatedAt(def.getUpdatedAt());
        return res;
    }
}
