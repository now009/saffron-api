package com.saffron.api.portal.service.menu;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.menu.MenuListDto;
import com.saffron.api.portal.dto.menu.MenuTreeDto;
import com.saffron.api.portal.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<MenuTreeDto> getMenuTree() {
        List<MenuTreeDto> allMenus = menuMapper.selectMenu();

        Map<String, MenuTreeDto> menuMap = new LinkedHashMap<>();
        for (MenuTreeDto menu : allMenus) {
            menu.setChildren(new ArrayList<>());
            menuMap.put(menu.getMenuId(), menu);
        }

        List<MenuTreeDto> roots = new ArrayList<>();
        for (MenuTreeDto menu : allMenus) {
            if (menu.getParentMenuId() == null) {
                roots.add(menu);
            } else {
                MenuTreeDto parent = menuMap.get(menu.getParentMenuId());
                if (parent != null) {
                    parent.getChildren().add(menu);
                }
            }
        }
        return roots;
    }

    @Override
    public List<MenuListDto> getMenus(String menuId, String menuName) {
        return menuMapper.selectMenuList(menuId, menuName);
    }

    @Override
    public ApiResponse deleteMenu(String menuId) {
        if (menuMapper.countMenu(menuId) == 0) {
            return ApiResponse.fail("메뉴가 존재하지 않습니다");
        }
        if (menuMapper.countChildMenus(menuId) > 0) {
            return ApiResponse.fail("하위 메뉴가 존재합니다");
        }
        menuMapper.deleteMenu(menuId);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public List<MenuListDto> getParentMenus() {
        return menuMapper.selectParentMenus();
    }

    @Override
    public String getNextMenuId() {
        return menuMapper.selectNextMenuId();
    }
}
