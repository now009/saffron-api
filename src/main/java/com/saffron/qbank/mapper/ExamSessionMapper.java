package com.saffron.qbank.mapper;

import com.saffron.qbank.domain.ExamSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamSessionMapper {
    List<ExamSession> selectAll(@Param("paperId") Integer paperId,
                                @Param("examineeName") String examineeName);
    ExamSession selectById(@Param("id") Integer id);
    int insert(ExamSession session);
    int updateSubmitted(@Param("id") Integer id);
    int updateTotalScore(@Param("id") Integer id, @Param("totalScore") Integer totalScore);
}
