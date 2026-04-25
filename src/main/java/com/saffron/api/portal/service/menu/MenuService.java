package com.saffron.api.portal.service.menu;

import com.saffron.api.portal.dto.menu.MenuGroupDto;
import com.saffron.api.portal.dto.menu.MenuTreeDto;

import java.util.List;

public interface MenuService {

    List<MenuGroupDto> getMenus();

    List<MenuTreeDto> getMenuTree();
}
