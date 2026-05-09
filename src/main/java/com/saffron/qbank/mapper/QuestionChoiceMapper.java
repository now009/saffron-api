package com.saffron.qbank.mapper;

import com.saffron.qbank.domain.QuestionChoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionChoiceMapper {
    List<QuestionChoice> selectByQuestionId(@Param("questionId") Integer questionId);
    QuestionChoice selectById(@Param("id") Integer id);
    int insert(QuestionChoice choice);
    int update(QuestionChoice choice);
    int deleteById(@Param("id") Integer id);
    List<Integer> selectCorrectChoiceIds(@Param("questionId") Integer questionId);
    int countCorrectChoices(@Param("questionId") Integer questionId);
}
