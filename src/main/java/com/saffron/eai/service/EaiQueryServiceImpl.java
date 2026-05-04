package com.saffron.eai.service;

import com.saffron.eai.domain.EaiDatasource;
import com.saffron.eai.dto.request.QueryValidationRequest;
import com.saffron.eai.dto.response.EaiApiResponse;
import com.saffron.eai.mapper.EaiDatasourceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class EaiQueryServiceImpl implements EaiQueryService {

    private final EaiDatasourceMapper datasourceMapper;

    @Override
    public EaiApiResponse<Void> validateQuery(QueryValidationRequest request) {
        String query = request.getQuery() != null ? request.getQuery().trim() : "";
        if (query.isEmpty()) {
            return EaiApiResponse.fail("query가 비어있습니다.");
        }
        if (containsMultipleStatements(query)) {
            return EaiApiResponse.fail("하나의 SQL만 검증할 수 있습니다.");
        }

        EaiDatasource ds = datasourceMapper.selectDatasourceByDatasourceId(request.getDatasourceId());
        if (ds == null) {
            return EaiApiResponse.fail("DataSource를 찾을 수 없습니다: " + request.getDatasourceId());
        }

        try {
            Class.forName(ds.getDriverClass());
        } catch (ClassNotFoundException e) {
            return EaiApiResponse.fail("Driver Class를 찾을 수 없습니다: " + ds.getDriverClass());
        }

        Properties props = new Properties();
        if (ds.getDbUsername() != null) props.setProperty("user", ds.getDbUsername());
        if (ds.getDbPassword() != null) props.setProperty("password", ds.getDbPassword());

        int loginTimeoutSec = (ds.getPoolTimeoutMs() != null && ds.getPoolTimeoutMs() > 0)
                ? Math.max(1, ds.getPoolTimeoutMs() / 1000) : 10;
        int prevTimeout = DriverManager.getLoginTimeout();
        DriverManager.setLoginTimeout(loginTimeoutSec);

        try (Connection conn = DriverManager.getConnection(ds.getJdbcUrl(), props)) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                if (ds.getQueryTimeoutSec() != null) {
                    pstmt.setQueryTimeout(ds.getQueryTimeoutSec());
                }
                // prepareStatement 단계에서 대부분의 드라이버가 syntax/object 검증 수행
                // (실제 execute 하지 않으므로 데이터 변경 없음)
                return EaiApiResponse.ok("Query 검증 성공 (" + ds.getDbType() + " 방언)");
            } finally {
                conn.rollback();
            }
        } catch (SQLException e) {
            log.warn("[EAI] Query 검증 실패 ds={} sqlState={} errorCode={} msg={}",
                    ds.getDatasourceId(), e.getSQLState(), e.getErrorCode(), e.getMessage());
            String detail = String.format("[%s/%d] %s",
                    e.getSQLState() != null ? e.getSQLState() : "-",
                    e.getErrorCode(),
                    e.getMessage());
            return EaiApiResponse.fail(detail);
        } finally {
            DriverManager.setLoginTimeout(prevTimeout);
        }
    }

    private boolean containsMultipleStatements(String sql) {
        boolean inSingle = false, inDouble = false;
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '\'' && !inDouble) inSingle = !inSingle;
            else if (c == '"' && !inSingle) inDouble = !inDouble;
            else if (c == ';' && !inSingle && !inDouble) {
                String rest = sql.substring(i + 1).trim();
                if (!rest.isEmpty()) return true;
            }
        }
        return false;
    }
}
