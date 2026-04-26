package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.menu.ProgramDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProgramMapper {

    int insertProgram(ProgramDto programDto);

    int updateProgram(ProgramDto programDto);

    int countProgram(@Param("programId") String programId);

    int countProgramInMenu(@Param("programId") String programId);

    int deleteProgram(@Param("programId") String programId);

    List<ProgramDto> selectProgramList(@Param("programId") String programId,
                                       @Param("programName") String programName,
                                       @Param("programUrl") String programUrl);

    String selectNextProgramId();
}
