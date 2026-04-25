package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.menu.MenuTreeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuTreeDto> selectMenu();
}
