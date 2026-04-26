package com.saffron.api.portal.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer emptyStringToNullCustomizer() {
        return builder -> builder.deserializerByType(String.class, new EmptyStringToNullDeserializer());
    }

    static class EmptyStringToNullDeserializer extends StdScalarDeserializer<String> {

        EmptyStringToNullDeserializer() {
            super(String.class);
        }

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String value = p.getValueAsString();
            return (value == null || value.trim().isEmpty()) ? null : value;
        }
    }
}
