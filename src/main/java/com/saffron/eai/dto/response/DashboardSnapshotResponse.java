package com.saffron.eai.dto.response;

import java.time.LocalDateTime;

public class DashboardSnapshotResponse {

    private long totalProcessed;
    private long successCount;
    private long failCount;
    private long dlqCount;
    private long activeInterfaces;
    private double successRate;
    private long avgProcessingMs;
    private LocalDateTime snapshotAt;

    public DashboardSnapshotResponse() {}

    public long getTotalProcessed() { return totalProcessed; }
    public void setTotalProcessed(long totalProcessed) { this.totalProcessed = totalProcessed; }

    public long getSuccessCount() { return successCount; }
    public void setSuccessCount(long successCount) { this.successCount = successCount; }

    public long getFailCount() { return failCount; }
    public void setFailCount(long failCount) { this.failCount = failCount; }

    public long getDlqCount() { return dlqCount; }
    public void setDlqCount(long dlqCount) { this.dlqCount = dlqCount; }

    public long getActiveInterfaces() { return activeInterfaces; }
    public void setActiveInterfaces(long activeInterfaces) { this.activeInterfaces = activeInterfaces; }

    public double getSuccessRate() { return successRate; }
    public void setSuccessRate(double successRate) { this.successRate = successRate; }

    public long getAvgProcessingMs() { return avgProcessingMs; }
    public void setAvgProcessingMs(long avgProcessingMs) { this.avgProcessingMs = avgProcessingMs; }

    public LocalDateTime getSnapshotAt() { return snapshotAt; }
    public void setSnapshotAt(LocalDateTime snapshotAt) { this.snapshotAt = snapshotAt; }
}
