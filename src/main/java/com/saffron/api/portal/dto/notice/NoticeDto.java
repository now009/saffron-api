package com.saffron.api.portal.dto.notice;

import java.time.LocalDateTime;

public class NoticeDto {

    private String noticeId;
    private String title;
    private String content;
    private String noticeType;
    private String isPinned;
    private String isPopup;
    private LocalDateTime popupStartDt;
    private LocalDateTime popupEndDt;
    private String targetDeptId;
    private Integer viewCount;
    private String attachYn;
    private String useYn;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updateUser;
    private LocalDateTime updatedDate;

    public NoticeDto() {}

    public String getNoticeId() { return noticeId; }
    public void setNoticeId(String noticeId) { this.noticeId = noticeId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getNoticeType() { return noticeType; }
    public void setNoticeType(String noticeType) { this.noticeType = noticeType; }

    public String getIsPinned() { return isPinned; }
    public void setIsPinned(String isPinned) { this.isPinned = isPinned; }

    public String getIsPopup() { return isPopup; }
    public void setIsPopup(String isPopup) { this.isPopup = isPopup; }

    public LocalDateTime getPopupStartDt() { return popupStartDt; }
    public void setPopupStartDt(LocalDateTime popupStartDt) { this.popupStartDt = popupStartDt; }

    public LocalDateTime getPopupEndDt() { return popupEndDt; }
    public void setPopupEndDt(LocalDateTime popupEndDt) { this.popupEndDt = popupEndDt; }

    public String getTargetDeptId() { return targetDeptId; }
    public void setTargetDeptId(String targetDeptId) { this.targetDeptId = targetDeptId; }

    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }

    public String getAttachYn() { return attachYn; }
    public void setAttachYn(String attachYn) { this.attachYn = attachYn; }

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
