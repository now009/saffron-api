package com.saffron.api.service.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saffron.api.dto.menu.MenuGroupDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TempMenuService implements MenuService {

    private final ObjectMapper objectMapper;

    public TempMenuService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<MenuGroupDto> getMenus() {
        try {
            InputStream is = new ClassPathResource("tempStorage/menu.json").getInputStream();
            return objectMapper.readValue(is, new TypeReference<List<MenuGroupDto>>() {});
        } catch (IOException e) {
            throw new RuntimeException("menu.json 로드 실패", e);
        }
    }
}
