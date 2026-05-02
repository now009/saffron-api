package com.saffron.portal.dto.menu;

public class MenuListDto {

    private Integer depth;
    private String menuNameTree;
    private String menuId;
    private String menuName;
    private String parentMenuId;
    private Integer menuLevel;
    private String menuIcon;
    private String menuDirYn;
    private Integer sortOrder;
    private String useYn;
    private String site;
    private String treePath;
    private String programId;
    private String programName;
    private String programUrl;

    public MenuListDto() {}

    public Integer getDepth() { return depth; }
    public void setDepth(Integer depth) { this.depth = depth; }

    public String getMenuNameTree() { return menuNameTree; }
    public void setMenuNameTree(String menuNameTree) { this.menuNameTree = menuNameTree; }

    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public String getParentMenuId() { return parentMenuId; }
    public void setParentMenuId(String parentMenuId) { this.parentMenuId = parentMenuId; }

    public Integer getMenuLevel() { return menuLevel; }
    public void setMenuLevel(Integer menuLevel) { this.menuLevel = menuLevel; }

    public String getMenuIcon() { return menuIcon; }
    public void setMenuIcon(String menuIcon) { this.menuIcon = menuIcon; }

    public String getMenuDirYn() { return menuDirYn; }
    public void setMenuDirYn(String menuDirYn) { this.menuDirYn = menuDirYn; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public String getTreePath() { return treePath; }
    public void setTreePath(String treePath) { this.treePath = treePath; }

    public String getProgramId() { return programId; }
    public void setProgramId(String programId) { this.programId = programId; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getProgramUrl() { return programUrl; }
    public void setProgramUrl(String programUrl) { this.programUrl = programUrl; }
}
