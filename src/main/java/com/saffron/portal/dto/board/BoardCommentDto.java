package com.saffron.portal.dto.board;

import java.time.LocalDateTime;

public class BoardCommentDto {

    private String commentId;
    private String postId;
    private String boardId;
    private String parentCommentId;
    private Integer depth;
    private String content;
    private String isAnonymous;
    private Integer likeCount;
    private String useYn;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updateUser;
    private LocalDateTime updatedDate;
    private String isLiked;

    public BoardCommentDto() {}

    public String getCommentId() { return commentId; }
    public void setCommentId(String commentId) { this.commentId = commentId; }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }

    public String getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(String parentCommentId) { this.parentCommentId = parentCommentId; }

    public Integer getDepth() { return depth; }
    public void setDepth(Integer depth) { this.depth = depth; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getIsAnonymous() { return isAnonymous; }
    public void setIsAnonymous(String isAnonymous) { this.isAnonymous = isAnonymous; }

    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }

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

    public String getIsLiked() { return isLiked; }
    public void setIsLiked(String isLiked) { this.isLiked = isLiked; }
}
