package com.saffron.api.portal.controller.user;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.user.DeptDto;
import com.saffron.api.portal.service.user.DeptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portal/depts")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<DeptDto>> list(@RequestBody(required = false) Map<String, String> params) {
        String deptId   = params != null ? params.get("deptId")   : null;
        String deptName = params != null ? params.get("deptName") : null;
        String deptCode = params != null ? params.get("deptCode") : null;
        return ResponseEntity.ok(deptService.getDepts(deptId, deptName, deptCode));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody DeptDto deptDto) {
        ApiResponse result = deptService.saveDept(deptDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(deptService.getDepts(null, null, null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody DeptDto deptDto) {
        ApiResponse result = deptService.updateDept(deptDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(deptService.getDepts(null, null, null));
    }

    @PostMapping("/delete/{deptId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String deptId) {
        return ResponseEntity.ok(deptService.deleteDept(deptId));
    }

    @GetMapping("/check-code/{deptCode}")
    public ResponseEntity<ApiResponse> checkCode(@PathVariable String deptCode) {
        return ResponseEntity.ok(deptService.checkDeptCode(deptCode));
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> nextId() {
        return ResponseEntity.ok(Map.of("deptId", deptService.getNextDeptId()));
    }
}
