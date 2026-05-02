package com.saffron.eai.adapter;

import com.saffron.eai.common.EaiAdapterException;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;
import com.saffron.eai.domain.EaiAdapterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component("restAdapter")
public class RestAdapter extends AbstractEaiAdapter {

    private final WebClient webClient;
    private final TokenProvider tokenProvider;

    public RestAdapter(WebClient.Builder builder, TokenProvider tokenProvider) {
        this.webClient = builder.build();
        this.tokenProvider = tokenProvider;
    }

    @Override
    public EaiResponse send(EaiMessage message) throws EaiAdapterException {
        EaiAdapterConfig config = message.getEndpointConfig();
        long startMs = System.currentTimeMillis();

        try {
            String responseBody = webClient
                    .method(HttpMethod.valueOf(config.getHttpMethod()))
                    .uri(config.getUrl())
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + tokenProvider.getToken(config))
                    .bodyValue(message.getPayload())
                    .retrieve()
                    .onStatus(status -> status.isError(), resp ->
                            resp.bodyToMono(String.class)
                                    .flatMap(body -> reactor.core.publisher.Mono.error(
                                            new EaiAdapterException("HTTP 오류: " + resp.statusCode() + " - " + body)))
                    )
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(config.getTimeoutMs() != null ? config.getTimeoutMs() : 5000))
                    .block();

            EaiResponse response = EaiResponse.success(responseBody, System.currentTimeMillis() - startMs);
            saveHistory(message, response, "SUCCESS");
            return response;

        } catch (Exception e) {
            EaiResponse errResp = EaiResponse.fail(e.getMessage(), System.currentTimeMillis() - startMs);
            saveHistory(message, errResp, "FAIL");
            throw new EaiAdapterException("REST 전송 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EaiMessage> receive(String interfaceId) throws EaiAdapterException {
        throw new UnsupportedOperationException("REST Pull은 스케줄러에서 직접 처리");
    }

    @Override
    public HealthStatus checkHealth() {
        try {
            webClient.get().uri("/health").retrieve()
                    .toBodilessEntity()
                    .timeout(Duration.ofSeconds(3))
                    .block();
            return HealthStatus.UP;
        } catch (Exception e) {
            return HealthStatus.DOWN;
        }
    }

    @Override
    public AdapterType getType() { return AdapterType.REST; }
}
