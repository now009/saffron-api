package com.saffron.api.portal.service.program;

import com.saffron.api.portal.dto.menu.ProgramDto;

import java.util.List;

public interface ProgramService {

    List<ProgramDto> getPrograms(String programId, String programName, String programUrl);
}
