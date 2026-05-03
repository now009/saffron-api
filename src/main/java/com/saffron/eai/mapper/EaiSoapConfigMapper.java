package com.saffron.eai.mapper;

import com.saffron.eai.domain.EaiSoapConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EaiSoapConfigMapper {

    List<EaiSoapConfig> selectSoapConfigList(@Param("interfaceId") String interfaceId);

    EaiSoapConfig selectSoapConfigById(@Param("id") Long id);

    EaiSoapConfig selectSoapConfigByInterfaceId(@Param("interfaceId") String interfaceId);

    int insertSoapConfig(EaiSoapConfig config);

    int updateSoapConfig(EaiSoapConfig config);

    int deleteSoapConfig(@Param("id") Long id);
}
