package com.saffron.eai.service;

import com.saffron.eai.dto.response.DashboardSnapshotResponse;
import com.saffron.eai.dto.response.InterfaceStatsResponse;

public interface EaiMonitoringService {

    DashboardSnapshotResponse getSnapshot();

    InterfaceStatsResponse getStats(String interfaceId, String period);
}
