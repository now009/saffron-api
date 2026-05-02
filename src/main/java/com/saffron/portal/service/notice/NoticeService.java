package com.saffron.portal.service.notice;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.notice.NoticeDto;

import java.util.List;

public interface NoticeService {

    List<NoticeDto> getNotices(String title, String noticeType, String useYn);

    NoticeDto getNotice(String noticeId);

    ApiResponse saveNotice(NoticeDto noticeDto);

    ApiResponse updateNotice(NoticeDto noticeDto);

    ApiResponse deleteNotice(String noticeId);

    String getNextNoticeId();
}
