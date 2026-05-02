package com.saffron.portal.dto.menu;

public class SubMenuDto {

    private String label;
    private String path;

    public SubMenuDto() {}

    public SubMenuDto(String label, String path) {
        this.label = label;
        this.path = path;
    }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
