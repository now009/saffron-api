package com.saffron.eai.mapper;

import com.saffron.eai.domain.EaiRestConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EaiRestConfigMapper {

    List<EaiRestConfig> selectRestConfigList(@Param("interfaceId") String interfaceId);

    EaiRestConfig selectRestConfigById(@Param("id") Long id);

    EaiRestConfig selectRestConfigByInterfaceId(@Param("interfaceId") String interfaceId);

    int insertRestConfig(EaiRestConfig config);

    int updateRestConfig(EaiRestConfig config);

    int deleteRestConfig(@Param("id") Long id);
}
