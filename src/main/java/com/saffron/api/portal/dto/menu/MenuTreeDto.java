package com.saffron.api.portal.dto.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeDto {

    private String menuId;
    private String parentMenuId;
    private String menuName;
    private Integer menuLevel;
    private String menuIcon;
    private Integer sortOrder;
    private List<MenuTreeDto> children = new ArrayList<>();

    public MenuTreeDto() {}

    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }

    public String getParentMenuId() { return parentMenuId; }
    public void setParentMenuId(String parentMenuId) { this.parentMenuId = parentMenuId; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public Integer getMenuLevel() { return menuLevel; }
    public void setMenuLevel(Integer menuLevel) { this.menuLevel = menuLevel; }

    public String getMenuIcon() { return menuIcon; }
    public void setMenuIcon(String menuIcon) { this.menuIcon = menuIcon; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public List<MenuTreeDto> getChildren() { return children; }
    public void setChildren(List<MenuTreeDto> children) { this.children = children; }
}
