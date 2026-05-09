package com.saffron.qbank.mapper;

import com.saffron.qbank.domain.ExamPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamPaperMapper {
    List<ExamPaper> selectAll(@Param("typeId") Integer typeId,
                              @Param("subjectId") Integer subjectId,
                              @Param("isActive") Boolean isActive);
    ExamPaper selectById(@Param("id") Integer id);
    int insert(ExamPaper examPaper);
    int update(ExamPaper examPaper);
    int deleteById(@Param("id") Integer id);
}
