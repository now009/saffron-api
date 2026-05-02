package com.saffron.portal.service.menu;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.menu.MenuDto;
import com.saffron.portal.dto.menu.MenuListDto;
import com.saffron.portal.dto.menu.MenuTreeDto;

import java.util.List;

public interface MenuService {

    ApiResponse saveMenu(MenuDto menuDto);

    ApiResponse updateMenu(MenuDto menuDto);

    List<MenuListDto> getMenus(String menuId, String menuName, String site);

    List<MenuTreeDto> getMenuTree(String site);

    ApiResponse deleteMenu(String menuId);

    List<MenuListDto> getParentMenus(String site);

    String getNextMenuId();
}
