package com.saffron.qbank.mapper;

import com.saffron.qbank.domain.AnswerSheet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnswerSheetMapper {
    List<AnswerSheet> selectBySessionId(@Param("sessionId") Integer sessionId);
    List<AnswerSheet> selectBySessionAndQuestion(@Param("sessionId") Integer sessionId,
                                                  @Param("questionId") Integer questionId);
    int insert(AnswerSheet answerSheet);
    int updateGrade(@Param("id") Integer id,
                    @Param("isCorrect") Boolean isCorrect);
}
