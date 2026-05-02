package com.saffron.portal.dto.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeDto {

    private String menuId;
    private String parentMenuId;
    private String menuName;
    private Integer menuLevel;
    private String menuIcon;
    private String menuDirYn;
    private String programId;
    private Integer sortOrder;
    private String site;
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

    public String getMenuDirYn() { return menuDirYn; }
    public void setMenuDirYn(String menuDirYn) { this.menuDirYn = menuDirYn; }

    public String getProgramId() { return programId; }
    public void setProgramId(String programId) { this.programId = programId; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public List<MenuTreeDto> getChildren() { return children; }
    public void setChildren(List<MenuTreeDto> children) { this.children = children; }
}
