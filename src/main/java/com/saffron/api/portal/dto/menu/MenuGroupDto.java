package com.saffron.api.portal.dto.menu;

import java.util.List;

public class MenuGroupDto {

    private String label;
    private String pathKey;
    private List<SubMenuDto> submenus;

    public MenuGroupDto() {}

    public MenuGroupDto(String label, String pathKey, List<SubMenuDto> submenus) {
        this.label = label;
        this.pathKey = pathKey;
        this.submenus = submenus;
    }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getPathKey() { return pathKey; }
    public void setPathKey(String pathKey) { this.pathKey = pathKey; }

    public List<SubMenuDto> getSubmenus() { return submenus; }
    public void setSubmenus(List<SubMenuDto> submenus) { this.submenus = submenus; }
}
