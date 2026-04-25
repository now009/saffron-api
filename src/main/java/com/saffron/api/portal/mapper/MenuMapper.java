package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.menu.MenuListDto;
import com.saffron.api.portal.dto.menu.MenuTreeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuListDto> selectMenuList(@Param("menuId") String menuId, @Param("menuName") String menuName);

    List<MenuTreeDto> selectMenu();
}
