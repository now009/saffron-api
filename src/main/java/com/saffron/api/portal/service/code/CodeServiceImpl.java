package com.saffron.api.portal.service.code;

import com.saffron.api.portal.dto.code.CodeDto;
import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.mapper.CodeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeServiceImpl implements CodeService {

    private final CodeMapper codeMapper;

    public CodeServiceImpl(CodeMapper codeMapper) {
        this.codeMapper = codeMapper;
    }

    @Override
    public List<CodeDto> getCodes(String parentCode, String code, String codeName) {
        return codeMapper.selectCodeList(parentCode, code, codeName);
    }

    @Override
    public ApiResponse saveCode(CodeDto codeDto) {
        if (codeMapper.countCode(codeDto.getCode()) > 0) {
            return ApiResponse.fail("이미 존재하는 코드입니다");
        }
        try {
            codeMapper.insertCode(codeDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateCode(CodeDto codeDto) {
        if (codeMapper.countCode(codeDto.getCode()) == 0) {
            return ApiResponse.fail("코드가 존재하지 않습니다");
        }
        try {
            codeMapper.updateCode(codeDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteCode(String code) {
        if (codeMapper.countCode(code) == 0) {
            return ApiResponse.fail("코드가 존재하지 않습니다");
        }
        if (codeMapper.countChildCodes(code) > 0) {
            return ApiResponse.fail("하위 코드가 존재합니다");
        }
        codeMapper.deleteCode(code);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public ApiResponse checkCode(String code) {
        if (codeMapper.countCode(code) > 0) {
            return ApiResponse.fail("이미 사용중인 코드입니다");
        }
        return ApiResponse.success("사용 가능한 코드입니다");
    }

    @Override
    public String getNextCode() {
        return codeMapper.selectNextCode();
    }
}
