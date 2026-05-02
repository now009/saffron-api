package com.saffron.portal.service.notice;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.notice.NoticeDto;
import com.saffron.portal.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public List<NoticeDto> getNotices(String title, String noticeType, String useYn) {
        return noticeMapper.selectNoticeList(title, noticeType, useYn);
    }

    @Override
    public NoticeDto getNotice(String noticeId) {
        NoticeDto notice = noticeMapper.selectNotice(noticeId);
        if (notice != null) {
            noticeMapper.incrementViewCount(noticeId);
        }
        return notice;
    }

    @Override
    public ApiResponse saveNotice(NoticeDto noticeDto) {
        if (noticeMapper.countNotice(noticeDto.getNoticeId()) > 0) {
            return ApiResponse.fail("이미 존재하는 공지사항ID입니다");
        }
        try {
            noticeMapper.insertNotice(noticeDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateNotice(NoticeDto noticeDto) {
        if (noticeMapper.countNotice(noticeDto.getNoticeId()) == 0) {
            return ApiResponse.fail("공지사항이 존재하지 않습니다");
        }
        try {
            noticeMapper.updateNotice(noticeDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteNotice(String noticeId) {
        if (noticeMapper.countNotice(noticeId) == 0) {
            return ApiResponse.fail("공지사항이 존재하지 않습니다");
        }
        noticeMapper.deleteNotice(noticeId);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public String getNextNoticeId() {
        return noticeMapper.selectNextNoticeId();
    }
}
