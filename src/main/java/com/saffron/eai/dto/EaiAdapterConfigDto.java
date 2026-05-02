package com.saffron.eai.dto;

import java.time.LocalDateTime;

public class EaiAdapterConfigDto {

    private Long id;
    private String interfaceId;
    private String adapterType;
    private String url;
    private String httpMethod;
    private Integer timeoutMs;
    private String authType;
    private String authValue;
    private String requestHeaders;
    private String datasourceId;
    private String statementId;
    private String operationType;
    private String remoteHost;
    private Integer remotePort;
    private String remoteUser;
    private String remotePassword;
    private String remotePath;
    private String donePath;
    private String filePattern;
    private String fileEncoding;
    private String extraConfig;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EaiAdapterConfigDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInterfaceId() { return interfaceId; }
    public void setInterfaceId(String interfaceId) { this.interfaceId = interfaceId; }

    public String getAdapterType() { return adapterType; }
    public void setAdapterType(String adapterType) { this.adapterType = adapterType; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getHttpMethod() { return httpMethod; }
    public void setHttpMethod(String httpMethod) { this.httpMethod = httpMethod; }

    public Integer getTimeoutMs() { return timeoutMs; }
    public void setTimeoutMs(Integer timeoutMs) { this.timeoutMs = timeoutMs; }

    public String getAuthType() { return authType; }
    public void setAuthType(String authType) { this.authType = authType; }

    public String getAuthValue() { return authValue; }
    public void setAuthValue(String authValue) { this.authValue = authValue; }

    public String getRequestHeaders() { return requestHeaders; }
    public void setRequestHeaders(String requestHeaders) { this.requestHeaders = requestHeaders; }

    public String getDatasourceId() { return datasourceId; }
    public void setDatasourceId(String datasourceId) { this.datasourceId = datasourceId; }

    public String getStatementId() { return statementId; }
    public void setStatementId(String statementId) { this.statementId = statementId; }

    public String getOperationType() { return operationType; }
    public void setOperationType(String operationType) { this.operationType = operationType; }

    public String getRemoteHost() { return remoteHost; }
    public void setRemoteHost(String remoteHost) { this.remoteHost = remoteHost; }

    public Integer getRemotePort() { return remotePort; }
    public void setRemotePort(Integer remotePort) { this.remotePort = remotePort; }

    public String getRemoteUser() { return remoteUser; }
    public void setRemoteUser(String remoteUser) { this.remoteUser = remoteUser; }

    public String getRemotePassword() { return remotePassword; }
    public void setRemotePassword(String remotePassword) { this.remotePassword = remotePassword; }

    public String getRemotePath() { return remotePath; }
    public void setRemotePath(String remotePath) { this.remotePath = remotePath; }

    public String getDonePath() { return donePath; }
    public void setDonePath(String donePath) { this.donePath = donePath; }

    public String getFilePattern() { return filePattern; }
    public void setFilePattern(String filePattern) { this.filePattern = filePattern; }

    public String getFileEncoding() { return fileEncoding; }
    public void setFileEncoding(String fileEncoding) { this.fileEncoding = fileEncoding; }

    public String getExtraConfig() { return extraConfig; }
    public void setExtraConfig(String extraConfig) { this.extraConfig = extraConfig; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
