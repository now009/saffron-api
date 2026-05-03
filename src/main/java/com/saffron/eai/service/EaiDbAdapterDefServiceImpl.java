package com.saffron.eai.service;

import com.saffron.eai.domain.EaiDbAdapterDef;
import com.saffron.eai.dto.EaiDbAdapterDefDto;
import com.saffron.eai.mapper.EaiDbAdapterDefMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EaiDbAdapterDefServiceImpl implements EaiDbAdapterDefService {

    private final EaiDbAdapterDefMapper dbAdapterDefMapper;

    @Override
    public List<EaiDbAdapterDefDto> findAll(String dbType, String direction) {
        return dbAdapterDefMapper.selectDbAdapterList(dbType, direction)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EaiDbAdapterDefDto findById(Long id) {
        EaiDbAdapterDef def = dbAdapterDefMapper.selectDbAdapterById(id);
        if (def == null) throw new IllegalArgumentException("DB 어댑터를 찾을 수 없습니다: " + id);
        return toDto(def);
    }

    @Override
    @Transactional
    public EaiDbAdapterDefDto create(EaiDbAdapterDefDto dto) {
        EaiDbAdapterDef def = toEntity(dto);
        dbAdapterDefMapper.insertDbAdapter(def);
        return toDto(def);
    }

    @Override
    @Transactional
    public EaiDbAdapterDefDto update(Long id, EaiDbAdapterDefDto dto) {
        EaiDbAdapterDef def = dbAdapterDefMapper.selectDbAdapterById(id);
        if (def == null) throw new IllegalArgumentException("DB 어댑터를 찾을 수 없습니다: " + id);

        def.setDbAdapterId(dto.getDbAdapterId());
        def.setDbType(dto.getDbType());
        def.setDbName(dto.getDbName());
        def.setDbIp(dto.getDbIp());
        def.setDbPort(dto.getDbPort());
        def.setDbId(dto.getDbId());
        if (dto.getDbPw() != null && !dto.getDbPw().isBlank()) {
            def.setDbPw(dto.getDbPw());
        }
        def.setDirection(dto.getDirection());

        dbAdapterDefMapper.updateDbAdapter(def);
        return toDto(def);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        dbAdapterDefMapper.deleteDbAdapter(id);
    }

    private EaiDbAdapterDefDto toDto(EaiDbAdapterDef def) {
        EaiDbAdapterDefDto dto = new EaiDbAdapterDefDto();
        dto.setId(def.getId());
        dto.setDbAdapterId(def.getDbAdapterId());
        dto.setDbType(def.getDbType());
        dto.setDbName(def.getDbName());
        dto.setDbIp(def.getDbIp());
        dto.setDbPort(def.getDbPort());
        dto.setDbId(def.getDbId());
        dto.setDbPw(null); // 비밀번호 응답 제외
        dto.setDirection(def.getDirection());
        return dto;
    }

    private EaiDbAdapterDef toEntity(EaiDbAdapterDefDto dto) {
        return EaiDbAdapterDef.builder()
                .dbAdapterId(dto.getDbAdapterId())
                .dbType(dto.getDbType())
                .dbName(dto.getDbName())
                .dbIp(dto.getDbIp())
                .dbPort(dto.getDbPort())
                .dbId(dto.getDbId())
                .dbPw(dto.getDbPw())
                .direction(dto.getDirection())
                .build();
    }
}
