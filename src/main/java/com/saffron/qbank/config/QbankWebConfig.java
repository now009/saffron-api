package com.saffron.qbank.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class QbankWebConfig {
    // 이미지 서빙은 QbankImageController (@GetMapping)에서 처리
    // Static ResourceHandler는 URL 디코딩 없이 파일명을 그대로 조회하므로 한글 파일명 지원 불가
}
