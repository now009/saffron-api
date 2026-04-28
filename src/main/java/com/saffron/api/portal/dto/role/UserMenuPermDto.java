package com.saffron.api.portal.dto.role;

public class UserMenuPermDto {

    private String menuId;
    private String parentMenuId;
    private String menuName;
    private Integer menuLevel;
    private String menuIcon;
    private Integer sortOrder;
    private String programUrl;
    private String canRead;
    private String canWrite;
    private String canUpdate;
    private String canDelete;

    public UserMenuPermDto() {}

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

    public String getProgramUrl() { return programUrl; }
    public void setProgramUrl(String programUrl) { this.programUrl = programUrl; }

    public String getCanRead() { return canRead; }
    public void setCanRead(String canRead) { this.canRead = canRead; }

    public String getCanWrite() { return canWrite; }
    public void setCanWrite(String canWrite) { this.canWrite = canWrite; }

    public String getCanUpdate() { return canUpdate; }
    public void setCanUpdate(String canUpdate) { this.canUpdate = canUpdate; }

    public String getCanDelete() { return canDelete; }
    public void setCanDelete(String canDelete) { this.canDelete = canDelete; }
}
