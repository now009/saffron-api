package com.saffron.api.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnProperty(prefix = "logging.http", name = "enabled", havingValue = "true")
public class HttpLoggingFilterConfig {

    @Value("${logging.http.max-body-size:4096}")
    private int maxBodySize;

    @Value("${logging.http.log-status-check:false}")
    private boolean logStatusCheck;

    /**
     * true  → Spring Security 체인보다 먼저 실행 (인증 거부된 401/403 도 로깅, dev 권장)
     * false → Security 뒤에서 실행 (인증 통과 후 요청만 로깅, 운영 권장)
     */
    @Value("${logging.http.before-security:false}")
    private boolean beforeSecurity;

    @Bean
    public FilterRegistrationBean<HttpLoggingFilter> httpLoggingFilterRegistration() {
        FilterRegistrationBean<HttpLoggingFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new HttpLoggingFilter(maxBodySize, logStatusCheck));
        reg.addUrlPatterns("/*");
        reg.setName("httpLoggingFilter");

        if (beforeSecurity) {
            // SecurityProperties.DEFAULT_FILTER_ORDER = -100 → -101 로 더 앞에 둔다
            reg.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER - 1);
        } else {
            // Security 체인 이후 (인증/인가 통과 후) 실행
            reg.setOrder(Ordered.LOWEST_PRECEDENCE);
        }
        return reg;
    }
}
