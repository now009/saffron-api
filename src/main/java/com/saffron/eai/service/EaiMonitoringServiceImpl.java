package com.saffron.eai.service;

import com.saffron.eai.dto.response.DashboardSnapshotResponse;
import com.saffron.eai.mapper.EaiInterfaceMapper;
import com.saffron.eai.mapper.EaiMessageHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EaiMonitoringServiceImpl implements EaiMonitoringService {

    private final EaiMessageHistoryMapper historyRepository;
    private final EaiInterfaceMapper interfaceRepository;

    @Override
    public DashboardSnapshotResponse getSnapshot() {
        // TODO: 집계 쿼리로 성능 개선
        List<?> all = historyRepository.selectHistoryList(null, null, null);
        long total = all.size();
        long success = historyRepository.selectHistoryList(null, "SUCCESS", null).size();
        long fail = historyRepository.selectHistoryList(null, "FAIL", null).size();
        long dlq = historyRepository.selectHistoryList(null, "DLQ", null).size();
        long active = interfaceRepository.selectInterfaceList("ACTIVE", null, null).size();

        DashboardSnapshotResponse snapshot = new DashboardSnapshotResponse();
        snapshot.setTotalProcessed(total);
        snapshot.setSuccessCount(success);
        snapshot.setFailCount(fail);
        snapshot.setDlqCount(dlq);
        snapshot.setActiveInterfaces(active);
        snapshot.setSuccessRate(total > 0 ? (double) success / total * 100 : 0.0);
        snapshot.setAvgProcessingMs(0L); // TODO: 평균 처리 시간 집계
        snapshot.setSnapshotAt(LocalDateTime.now());
        return snapshot;
    }
}
