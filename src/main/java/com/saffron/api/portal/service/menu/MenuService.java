package com.saffron.api.portal.service.menu;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.menu.MenuDto;
import com.saffron.api.portal.dto.menu.MenuListDto;
import com.saffron.api.portal.dto.menu.MenuTreeDto;

import java.util.List;

public interface MenuService {

    ApiResponse saveMenu(MenuDto menuDto);

    ApiResponse updateMenu(MenuDto menuDto);

    List<MenuListDto> getMenus(String menuId, String menuName);

    List<MenuTreeDto> getMenuTree();

    ApiResponse deleteMenu(String menuId);

    List<MenuListDto> getParentMenus();

    String getNextMenuId();
}
