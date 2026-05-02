package com.saffron.eai.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saffron.eai.common.EaiAdapterException;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;
import com.saffron.eai.domain.EaiAdapterConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component("dbAdapter")
public class DbAdapter extends AbstractEaiAdapter {

    private final SqlSessionFactory sqlSessionFactory;
    private final ObjectMapper objectMapper;

    public DbAdapter(SqlSessionFactory sqlSessionFactory, ObjectMapper objectMapper) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public EaiResponse send(EaiMessage message) throws EaiAdapterException {
        EaiAdapterConfig config = message.getEndpointConfig();
        long startMs = System.currentTimeMillis();

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> params = objectMapper.readValue(message.getPayload(), Map.class);
            Object result;

            switch (config.getOperationType() != null ? config.getOperationType() : "") {
                case "QUERY"  -> result = session.selectList(config.getStatementId(), params);
                case "INSERT" -> { result = session.insert(config.getStatementId(), params); session.commit(); }
                case "UPDATE" -> { result = session.update(config.getStatementId(), params); session.commit(); }
                default -> throw new EaiAdapterException("미지원 operationType: " + config.getOperationType());
            }

            String resultJson = objectMapper.writeValueAsString(result);
            EaiResponse response = EaiResponse.success(resultJson, System.currentTimeMillis() - startMs);
            saveHistory(message, response, "SUCCESS");
            logTransaction(message, response);
            return response;

        } catch (EaiAdapterException e) {
            throw e;
        } catch (Exception e) {
            EaiResponse errResp = EaiResponse.fail(e.getMessage(), System.currentTimeMillis() - startMs);
            saveHistory(message, errResp, "FAIL");
            throw new EaiAdapterException("DB 처리 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EaiMessage> receive(String interfaceId) throws EaiAdapterException {
        // TODO: Pull 방식 - SELECT로 미처리 레코드 조회 후 EaiMessage 변환
        return List.of();
    }

    @Override
    public HealthStatus checkHealth() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return HealthStatus.UP;
        } catch (Exception e) {
            return HealthStatus.DOWN;
        }
    }

    @Override
    public AdapterType getType() { return AdapterType.DB; }
}
