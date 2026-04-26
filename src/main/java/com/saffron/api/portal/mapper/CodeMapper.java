package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.code.CodeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeMapper {

    List<CodeDto> selectCodeList(@Param("parentCode") String parentCode,
                                 @Param("code") String code,
                                 @Param("codeName") String codeName);

    List<CodeDto> selectAllCodes();

    int insertCode(CodeDto codeDto);

    int updateCode(CodeDto codeDto);

    int countCode(@Param("code") String code);

    int countChildCodes(@Param("code") String code);

    int deleteCode(@Param("code") String code);

    String selectNextCode();
}
