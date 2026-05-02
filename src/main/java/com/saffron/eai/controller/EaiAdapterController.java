package com.saffron.eai.controller;

import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.dto.request.AdapterConfigRequest;
import com.saffron.eai.service.EaiAdapterService;
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
@RequestMapping("/api/eai/adapters")
@RequiredArgsConstructor
public class EaiAdapterController {

    private final EaiAdapterService adapterService;

    @GetMapping
    public ResponseEntity<List<EaiAdapterConfig>> list(
            @RequestParam(required = false) String interfaceId) {
        return ResponseEntity.ok(adapterService.findAll(interfaceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EaiAdapterConfig> get(@PathVariable Long id) {
        EaiAdapterConfig config = adapterService.findById(id);
        if (config == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(config);
    }

    @PostMapping
    public ResponseEntity<EaiAdapterConfig> create(@RequestBody @Valid AdapterConfigRequest request) {
        return ResponseEntity.ok(adapterService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EaiAdapterConfig> update(@PathVariable Long id,
                                                    @RequestBody @Valid AdapterConfigRequest request) {
        return ResponseEntity.ok(adapterService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adapterService.delete(id);
        return ResponseEntity.ok().build();
    }
}
