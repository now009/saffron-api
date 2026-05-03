package com.saffron.eai.controller;

import com.saffron.eai.dto.EaiRestConfigDto;
import com.saffron.eai.service.EaiRestConfigService;
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
@RequestMapping("/eai/rest-configs")
@RequiredArgsConstructor
public class EaiRestConfigController {

    private final EaiRestConfigService restConfigService;

    @GetMapping
    public ResponseEntity<List<EaiRestConfigDto>> list(
            @RequestParam(required = false) String interfaceId) {
        return ResponseEntity.ok(restConfigService.findAll(interfaceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EaiRestConfigDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(restConfigService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EaiRestConfigDto> create(@RequestBody @Valid EaiRestConfigDto dto) {
        return ResponseEntity.ok(restConfigService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EaiRestConfigDto> update(@PathVariable Long id,
                                                    @RequestBody @Valid EaiRestConfigDto dto) {
        return ResponseEntity.ok(restConfigService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restConfigService.delete(id);
        return ResponseEntity.ok().build();
    }
}
