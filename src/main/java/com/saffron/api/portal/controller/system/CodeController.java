package com.saffron.api.portal.controller.system;

import com.saffron.api.portal.dto.code.CodeDto;
import com.saffron.api.portal.dto.code.CodeTreeDto;
import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.service.code.CodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portal/codes")
public class CodeController {

    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<CodeTreeDto>> list(@RequestBody(required = false) Map<String, String> params) {
        String parentCode = params != null ? params.get("parentCode") : null;
        return ResponseEntity.ok(codeService.getCodes(parentCode));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CodeDto codeDto) {
        ApiResponse result = codeService.saveCode(codeDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(codeService.getCodes(null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody CodeDto codeDto) {
        ApiResponse result = codeService.updateCode(codeDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(codeService.getCodes(null));
    }

    @PostMapping("/delete/{code}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String code) {
        return ResponseEntity.ok(codeService.deleteCode(code));
    }

    @GetMapping("/check-code/{code}")
    public ResponseEntity<ApiResponse> checkCode(@PathVariable String code) {
        return ResponseEntity.ok(codeService.checkCode(code));
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> nextId() {
        return ResponseEntity.ok(Map.of("code", codeService.getNextCode()));
    }
}
