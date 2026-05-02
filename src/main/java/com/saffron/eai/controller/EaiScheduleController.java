package com.saffron.eai.controller;

import com.saffron.eai.domain.EaiSchedule;
import com.saffron.eai.service.EaiSchedulerService;
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
@RequestMapping("/api/eai/schedules")
@RequiredArgsConstructor
public class EaiScheduleController {

    private final EaiSchedulerService schedulerService;

    @GetMapping
    public ResponseEntity<List<EaiSchedule>> list(
            @RequestParam(required = false) String interfaceId) {
        return ResponseEntity.ok(schedulerService.findAll(interfaceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EaiSchedule> get(@PathVariable Long id) {
        return ResponseEntity.ok(schedulerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EaiSchedule> create(@RequestBody EaiSchedule schedule) {
        return ResponseEntity.ok(schedulerService.save(schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EaiSchedule> update(@PathVariable Long id,
                                               @RequestBody EaiSchedule schedule) {
        schedule.setId(id);
        return ResponseEntity.ok(schedulerService.save(schedule));
    }

    @PostMapping("/{id}/run")
    public ResponseEntity<Void> runNow(@PathVariable Long id) {
        schedulerService.executeNow(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        schedulerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
