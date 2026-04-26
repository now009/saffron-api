package com.saffron.api.portal.controller.menu;

import com.saffron.api.portal.dto.menu.ProgramDto;
import com.saffron.api.portal.service.program.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portal/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<ProgramDto>> list(@RequestBody(required = false) Map<String, String> params) {
        String programId   = params != null ? params.get("programId")   : null;
        String programName = params != null ? params.get("programName") : null;
        String programUrl  = params != null ? params.get("programUrl")  : null;
        return ResponseEntity.ok(programService.getPrograms(programId, programName, programUrl));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PostMapping("/delete/{programId}")
    public ResponseEntity<?> delete(@PathVariable String programId) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
