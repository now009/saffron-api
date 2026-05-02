package com.saffron.portal.service.program;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.menu.ProgramDto;

import java.util.List;

public interface ProgramService {

    ApiResponse saveProgram(ProgramDto programDto);

    ApiResponse updateProgram(ProgramDto programDto);

    ApiResponse deleteProgram(String programId);

    List<ProgramDto> getPrograms(String programId, String programName, String programUrl);

    String getNextProgramId();
}
