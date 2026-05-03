package com.saffron.eai.controller;

import com.saffron.eai.dto.EaiDbAdapterConfigDto;
import com.saffron.eai.service.EaiDbAdapterConfigService;
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

import java.util.List;

@RestController
@RequestMapping("/eai/db-adapter-configs")
@RequiredArgsConstructor
public class EaiDbAdapterConfigController {

    private final EaiDbAdapterConfigService dbAdapterConfigService;

    @GetMapping
    public ResponseEntity<List<EaiDbAdapterConfigDto>> list(
            @RequestParam(required = false) String interfaceId,
            @RequestParam(required = false) String datasourceId) {
        return ResponseEntity.ok(dbAdapterConfigService.findAll(interfaceId, datasourceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EaiDbAdapterConfigDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(dbAdapterConfigService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EaiDbAdapterConfigDto> create(@RequestBody @Valid EaiDbAdapterConfigDto dto) {
        return ResponseEntity.ok(dbAdapterConfigService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EaiDbAdapterConfigDto> update(@PathVariable Long id,
                                                         @RequestBody @Valid EaiDbAdapterConfigDto dto) {
        return ResponseEntity.ok(dbAdapterConfigService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dbAdapterConfigService.delete(id);
        return ResponseEntity.ok().build();
    }
}
