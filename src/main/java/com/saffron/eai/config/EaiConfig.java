package com.saffron.eai.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EaiConfig {

    // DbAdapter가 요구하는 Map<String, SqlSessionFactory>
    // 현재는 단일 DataSource만 사용하므로 "default" 키로 등록
    @Bean
    public Map<String, SqlSessionFactory> sessionFactories(SqlSessionFactory sqlSessionFactory) {
        Map<String, SqlSessionFactory> map = new HashMap<>();
        map.put("default", sqlSessionFactory);
        return map;
    }
}
