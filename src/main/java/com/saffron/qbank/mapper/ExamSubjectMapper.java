package com.saffron.qbank.mapper;

import com.saffron.qbank.domain.ExamSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamSubjectMapper {
    List<ExamSubject> selectAll();
    ExamSubject selectById(@Param("id") Integer id);
    int insert(ExamSubject examSubject);
    int update(ExamSubject examSubject);
    int deleteById(@Param("id") Integer id);
}
