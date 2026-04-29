package com.saffron.api.portal.dto.board;

import java.time.LocalDateTime;

public class BoardMasterDto {

    private String boardId;
    private String boardName;
    private String boardType;
    private String description;
    private String allowComment;
    private String allowAttach;
    private String allowAnonymous;
    private String allowSearch;
    private String readRole;
    private String writeRole;
    private Integer listCount;
    private Integer newDays;
    private Integer sortOrder;
    private String useYn;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updateUser;
    private LocalDateTime updatedDate;

    public BoardMasterDto() {}

    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }

    public String getBoardName() { return boardName; }
    public void setBoardName(String boardName) { this.boardName = boardName; }

    public String getBoardType() { return boardType; }
    public void setBoardType(String boardType) { this.boardType = boardType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAllowComment() { return allowComment; }
    public void setAllowComment(String allowComment) { this.allowComment = allowComment; }

    public String getAllowAttach() { return allowAttach; }
    public void setAllowAttach(String allowAttach) { this.allowAttach = allowAttach; }

    public String getAllowAnonymous() { return allowAnonymous; }
    public void setAllowAnonymous(String allowAnonymous) { this.allowAnonymous = allowAnonymous; }

    public String getAllowSearch() { return allowSearch; }
    public void setAllowSearch(String allowSearch) { this.allowSearch = allowSearch; }

    public String getReadRole() { return readRole; }
    public void setReadRole(String readRole) { this.readRole = readRole; }

    public String getWriteRole() { return writeRole; }
    public void setWriteRole(String writeRole) { this.writeRole = writeRole; }

    public Integer getListCount() { return listCount; }
    public void setListCount(Integer listCount) { this.listCount = listCount; }

    public Integer getNewDays() { return newDays; }
    public void setNewDays(Integer newDays) { this.newDays = newDays; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getCreatedUser() { return createdUser; }
    public void setCreatedUser(String createdUser) { this.createdUser = createdUser; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getUpdateUser() { return updateUser; }
    public void setUpdateUser(String updateUser) { this.updateUser = updateUser; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
