package com.saffron.qbank.mapper;

import com.saffron.qbank.domain.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    List<Question> selectByPaperId(@Param("paperId") Integer paperId);
    Question selectById(@Param("id") Integer id);
    int insert(Question question);
    int update(Question question);
    int deleteById(@Param("id") Integer id);
}
