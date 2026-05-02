package com.saffron.portal.mapper;

import com.saffron.portal.dto.notice.NoticeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<NoticeDto> selectNoticeList(@Param("title") String title,
                                     @Param("noticeType") String noticeType,
                                     @Param("useYn") String useYn);

    NoticeDto selectNotice(@Param("noticeId") String noticeId);

    int insertNotice(NoticeDto noticeDto);

    int updateNotice(NoticeDto noticeDto);

    int deleteNotice(@Param("noticeId") String noticeId);

    int countNotice(@Param("noticeId") String noticeId);

    int incrementViewCount(@Param("noticeId") String noticeId);

    String selectNextNoticeId();
}
