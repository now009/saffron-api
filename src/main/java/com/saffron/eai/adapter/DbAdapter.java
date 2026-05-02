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

    private final Map<String, SqlSessionFactory> sessionFactories;
    private final ObjectMapper objectMapper;

    public DbAdapter(Map<String, SqlSessionFactory> sessionFactories, ObjectMapper objectMapper) {
        this.sessionFactories = sessionFactories;
        this.objectMapper = objectMapper;
    }

    @Override
    public EaiResponse send(EaiMessage message) throws EaiAdapterException {
        EaiAdapterConfig config = message.getEndpointConfig();
        SqlSessionFactory factory = sessionFactories.get(config.getDatasourceId());
        if (factory == null) throw new EaiAdapterException("DataSource 미등록: " + config.getDatasourceId());

        long startMs = System.currentTimeMillis();
        try (SqlSession session = factory.openSession()) {
            Map<String, Object> params = objectMapper.readValue(message.getPayload(), Map.class);
            Object result;

            switch (config.getOperationType()) {
                case "QUERY"     -> result = session.selectList(config.getStatementId(), params);
                case "INSERT"    -> { result = session.insert(config.getStatementId(), params); session.commit(); }
                case "UPDATE"    -> { result = session.update(config.getStatementId(), params); session.commit(); }
                case "PROCEDURE" -> result = callProcedure(session, config, params);
                default -> throw new EaiAdapterException("미지원 operationType: " + config.getOperationType());
            }

            String resultJson = objectMapper.writeValueAsString(result);
            EaiResponse response = EaiResponse.success(resultJson, System.currentTimeMillis() - startMs);
            saveHistory(message, response, "SUCCESS");
            return response;

        } catch (EaiAdapterException e) {
            throw e;
        } catch (Exception e) {
            throw new EaiAdapterException("DB 처리 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EaiMessage> receive(String interfaceId) throws EaiAdapterException {
        return List.of();
    }

    @Override
    public HealthStatus checkHealth() {
        SqlSessionFactory factory = sessionFactories.values().stream().findFirst().orElse(null);
        if (factory == null) return HealthStatus.UNKNOWN;
        try (SqlSession session = factory.openSession()) {
            return HealthStatus.UP;
        } catch (Exception e) {
            return HealthStatus.DOWN;
        }
    }

    @Override
    public AdapterType getType() { return AdapterType.DB; }

    private Object callProcedure(SqlSession session, EaiAdapterConfig config, Map<String, Object> params) {
        session.update(config.getStatementId(), params);
        session.commit();
        return params;
    }
}
