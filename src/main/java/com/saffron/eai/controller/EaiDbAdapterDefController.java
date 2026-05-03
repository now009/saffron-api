package com.saffron.eai.controller;

import com.saffron.eai.dto.EaiDbAdapterDefDto;
import com.saffron.eai.service.EaiDbAdapterDefService;
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
@RequestMapping("/eai/db-adapters")
@RequiredArgsConstructor
public class EaiDbAdapterDefController {

    private final EaiDbAdapterDefService dbAdapterDefService;

    @GetMapping
    public ResponseEntity<List<EaiDbAdapterDefDto>> list(
            @RequestParam(required = false) String dbType,
            @RequestParam(required = false) String direction) {
        return ResponseEntity.ok(dbAdapterDefService.findAll(dbType, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EaiDbAdapterDefDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(dbAdapterDefService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EaiDbAdapterDefDto> create(@RequestBody @Valid EaiDbAdapterDefDto dto) {
        return ResponseEntity.ok(dbAdapterDefService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EaiDbAdapterDefDto> update(@PathVariable Long id,
                                                      @RequestBody @Valid EaiDbAdapterDefDto dto) {
        return ResponseEntity.ok(dbAdapterDefService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dbAdapterDefService.delete(id);
        return ResponseEntity.ok().build();
    }
}
