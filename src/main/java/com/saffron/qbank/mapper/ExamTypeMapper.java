package com.saffron.qbank.mapper;

import com.saffron.qbank.domain.ExamType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamTypeMapper {
    List<ExamType> selectAll();
    ExamType selectById(@Param("id") Integer id);
    int insert(ExamType examType);
    int update(ExamType examType);
    int deleteById(@Param("id") Integer id);
}
