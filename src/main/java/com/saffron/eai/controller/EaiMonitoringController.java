package com.saffron.eai.controller;

import com.saffron.eai.dto.response.DashboardSnapshotResponse;
import com.saffron.eai.dto.response.InterfaceStatsResponse;
import com.saffron.eai.service.EaiMonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/eai/monitoring")
@RequiredArgsConstructor
public class EaiMonitoringController {

    private final EaiMonitoringService monitoringService;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DashboardSnapshotResponse> stream() {
        return Flux.interval(java.time.Duration.ofSeconds(5))
                .map(tick -> monitoringService.getSnapshot())
                .share();
    }

    @GetMapping("/snapshot")
    public ResponseEntity<DashboardSnapshotResponse> snapshot() {
        return ResponseEntity.ok(monitoringService.getSnapshot());
    }

    @GetMapping("/stats/{interfaceId}")
    public ResponseEntity<InterfaceStatsResponse> stats(@PathVariable String interfaceId,
                                                         @RequestParam String period) {
        return ResponseEntity.ok(monitoringService.getStats(interfaceId, period));
    }
}
