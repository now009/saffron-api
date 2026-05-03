package com.saffron.eai.adapter;

import com.saffron.eai.domain.EaiAdapterConfig;
import org.springframework.stereotype.Component;

@Component
public class DefaultTokenProvider implements TokenProvider {

    @Override
    public String getToken(EaiAdapterConfig config) {
        // authType: BEARER, BASIC, NONE
        // authValue: 설정된 토큰 또는 자격증명 반환
        return config.getAuthValue() != null ? config.getAuthValue() : "";
    }
}
