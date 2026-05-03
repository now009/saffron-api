package com.saffron.eai.controller;

import com.saffron.eai.dto.EaiDatasourceDto;
import com.saffron.eai.service.EaiDatasourceService;
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
@RequestMapping("/eai/datasources")
@RequiredArgsConstructor
public class EaiDatasourceController {

    private final EaiDatasourceService datasourceService;

    @GetMapping
    public ResponseEntity<List<EaiDatasourceDto>> list(
            @RequestParam(required = false) String dbType,
            @RequestParam(required = false) Boolean isActive) {
        return ResponseEntity.ok(datasourceService.findAll(dbType, isActive));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EaiDatasourceDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(datasourceService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EaiDatasourceDto> create(@RequestBody @Valid EaiDatasourceDto dto) {
        return ResponseEntity.ok(datasourceService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EaiDatasourceDto> update(@PathVariable Long id,
                                                    @RequestBody @Valid EaiDatasourceDto dto) {
        return ResponseEntity.ok(datasourceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        datasourceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
