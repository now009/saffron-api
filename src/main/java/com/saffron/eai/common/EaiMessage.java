package com.saffron.eai.common;

import com.saffron.eai.domain.EaiAdapterConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EaiMessage {

    private String messageId;
    private String interfaceId;
    private String sourceSystem;
    private String targetSystem;
    private String direction;
    private String payload;
    private EaiAdapterConfig endpointConfig;

    public EaiMessage copy() {
        return EaiMessage.builder()
                .messageId(UUID.randomUUID().toString())
                .interfaceId(this.interfaceId)
                .sourceSystem(this.sourceSystem)
                .targetSystem(this.targetSystem)
                .direction(this.direction)
                .payload(this.payload)
                .endpointConfig(this.endpointConfig)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
        private String messageId;
        private String interfaceId;
        private String sendSystem;
        private LocalDateTime sendTimestamp;
    }
}
