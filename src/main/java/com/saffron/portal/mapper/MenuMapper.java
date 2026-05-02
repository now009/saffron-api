package com.saffron.portal.mapper;

import com.saffron.portal.dto.menu.MenuDto;
import com.saffron.portal.dto.menu.MenuListDto;
import com.saffron.portal.dto.menu.MenuTreeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    int updateMenu(MenuDto menuDto);

    int insertMenu(MenuDto menuDto);

    List<MenuListDto> selectMenuList(@Param("menuId") String menuId,
                                     @Param("menuName") String menuName,
                                     @Param("site") String site);

    List<MenuTreeDto> selectMenu(@Param("site") String site);

    int countMenu(@Param("menuId") String menuId);

    int countChildMenus(@Param("menuId") String menuId);

    int deleteMenu(@Param("menuId") String menuId);

    List<MenuListDto> selectParentMenus(@Param("site") String site);

    String selectNextMenuId();
}
