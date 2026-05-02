package com.saffron.portal.dto.board;

import java.time.LocalDateTime;

public class BoardLikeDto {

    private String likeId;
    private String boardId;
    private String targetType;
    private String targetId;
    private String createdUser;
    private LocalDateTime createdDate;

    public BoardLikeDto() {}

    public String getLikeId() { return likeId; }
    public void setLikeId(String likeId) { this.likeId = likeId; }

    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }

    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }

    public String getCreatedUser() { return createdUser; }
    public void setCreatedUser(String createdUser) { this.createdUser = createdUser; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
