package com.saffron.api.service.menu;

import com.saffron.api.dto.menu.MenuGroupDto;

import java.util.List;

public interface MenuService {

    List<MenuGroupDto> getMenus();
}
