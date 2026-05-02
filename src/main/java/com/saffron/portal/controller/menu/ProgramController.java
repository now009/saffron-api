package com.saffron.portal.controller.menu;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.menu.ProgramDto;
import com.saffron.portal.service.program.ProgramService;
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
    public ResponseEntity<?> save(@RequestBody ProgramDto programDto) {
        ApiResponse result = programService.saveProgram(programDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(programService.getPrograms(null, null, null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody ProgramDto programDto) {
        ApiResponse result = programService.updateProgram(programDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(programService.getPrograms(null, null, null));
    }

    @PostMapping("/delete/{programId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String programId) {
        return ResponseEntity.ok(programService.deleteProgram(programId));
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> nextId() {
        return ResponseEntity.ok(Map.of("programId", programService.getNextProgramId()));
    }
}
