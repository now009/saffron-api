package com.saffron.eai.controller;

import com.saffron.eai.dto.EaiSoapConfigDto;
import com.saffron.eai.service.EaiSoapConfigService;
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
@RequestMapping("/eai/soap-configs")
@RequiredArgsConstructor
public class EaiSoapConfigController {

    private final EaiSoapConfigService soapConfigService;

    @GetMapping
    public ResponseEntity<List<EaiSoapConfigDto>> list(
            @RequestParam(required = false) String interfaceId) {
        return ResponseEntity.ok(soapConfigService.findAll(interfaceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EaiSoapConfigDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(soapConfigService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EaiSoapConfigDto> create(@RequestBody @Valid EaiSoapConfigDto dto) {
        return ResponseEntity.ok(soapConfigService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EaiSoapConfigDto> update(@PathVariable Long id,
                                                    @RequestBody @Valid EaiSoapConfigDto dto) {
        return ResponseEntity.ok(soapConfigService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        soapConfigService.delete(id);
        return ResponseEntity.ok().build();
    }
}
