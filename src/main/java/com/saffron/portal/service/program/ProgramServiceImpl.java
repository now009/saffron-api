package com.saffron.portal.service.program;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.menu.ProgramDto;
import com.saffron.portal.mapper.ProgramMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramMapper programMapper;

    public ProgramServiceImpl(ProgramMapper programMapper) {
        this.programMapper = programMapper;
    }

    @Override
    public ApiResponse saveProgram(ProgramDto programDto) {
        if (programMapper.countProgram(programDto.getProgramId()) > 0) {
            return ApiResponse.fail("이미 존재하는 프로그램ID입니다");
        }
        try {
            programMapper.insertProgram(programDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateProgram(ProgramDto programDto) {
        if (programMapper.countProgram(programDto.getProgramId()) == 0) {
            return ApiResponse.fail("프로그램이 존재하지 않습니다");
        }
        try {
            programMapper.updateProgram(programDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteProgram(String programId) {
        if (programMapper.countProgram(programId) == 0) {
            return ApiResponse.fail("프로그램이 존재하지 않습니다");
        }
        if (programMapper.countProgramInMenu(programId) > 0) {
            return ApiResponse.fail("메뉴에서 사용중인 프로그램입니다");
        }
        programMapper.deleteProgram(programId);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public List<ProgramDto> getPrograms(String programId, String programName, String programUrl) {
        return programMapper.selectProgramList(programId, programName, programUrl);
    }

    @Override
    public String getNextProgramId() {
        return programMapper.selectNextProgramId();
    }
}
