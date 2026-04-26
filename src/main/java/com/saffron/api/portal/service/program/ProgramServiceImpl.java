package com.saffron.api.portal.service.program;

import com.saffron.api.portal.dto.menu.ProgramDto;
import com.saffron.api.portal.mapper.ProgramMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramMapper programMapper;

    public ProgramServiceImpl(ProgramMapper programMapper) {
        this.programMapper = programMapper;
    }

    @Override
    public List<ProgramDto> getPrograms(String programId, String programName, String programUrl) {
        return programMapper.selectProgramList(programId, programName, programUrl);
    }
}
