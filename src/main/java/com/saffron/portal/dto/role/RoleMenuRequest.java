package com.saffron.portal.dto.role;

public class RoleMenuRequest {

    private String menuId;
    private String useYn;

    public RoleMenuRequest() {}

    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
}
