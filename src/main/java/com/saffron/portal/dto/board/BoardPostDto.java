package com.saffron.portal.dto.board;

import java.time.LocalDateTime;

public class BoardPostDto {

    private String postId;
    private String boardId;
    private String parentPostId;
    private Integer depth;
    private String title;
    private String content;
    private String category;
    private String tags;
    private String isPinned;
    private String isSecret;
    private String isAnonymous;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer attachCount;
    private String targetDeptId;
    private String useYn;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updateUser;
    private LocalDateTime updatedDate;
    private String isLiked;
    private String boardName;

    public BoardPostDto() {}

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }

    public String getParentPostId() { return parentPostId; }
    public void setParentPostId(String parentPostId) { this.parentPostId = parentPostId; }

    public Integer getDepth() { return depth; }
    public void setDepth(Integer depth) { this.depth = depth; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getIsPinned() { return isPinned; }
    public void setIsPinned(String isPinned) { this.isPinned = isPinned; }

    public String getIsSecret() { return isSecret; }
    public void setIsSecret(String isSecret) { this.isSecret = isSecret; }

    public String getIsAnonymous() { return isAnonymous; }
    public void setIsAnonymous(String isAnonymous) { this.isAnonymous = isAnonymous; }

    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }

    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }

    public Integer getCommentCount() { return commentCount; }
    public void setCommentCount(Integer commentCount) { this.commentCount = commentCount; }

    public Integer getAttachCount() { return attachCount; }
    public void setAttachCount(Integer attachCount) { this.attachCount = attachCount; }

    public String getTargetDeptId() { return targetDeptId; }
    public void setTargetDeptId(String targetDeptId) { this.targetDeptId = targetDeptId; }

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

    public String getBoardName() { return boardName; }
    public void setBoardName(String boardName) { this.boardName = boardName; }
}
