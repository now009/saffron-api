package com.saffron.api.portal.service.code;

import com.saffron.api.portal.dto.code.CodeDto;
import com.saffron.api.portal.dto.code.CodeTreeDto;
import com.saffron.api.portal.dto.common.ApiResponse;

import java.util.List;

public interface CodeService {

    List<CodeTreeDto> getCodes(String parentCode);

    ApiResponse saveCode(CodeDto codeDto);

    ApiResponse updateCode(CodeDto codeDto);

    ApiResponse deleteCode(String code);

    ApiResponse checkCode(String code);

    String getNextCode();
}
