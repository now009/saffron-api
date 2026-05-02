package com.saffron.eai.repository;

import com.saffron.eai.domain.EaiAdapterConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EaiAdapterConfigRepository {

    List<EaiAdapterConfig> selectAdapterConfigList(@Param("interfaceId") String interfaceId);

    EaiAdapterConfig selectAdapterConfigById(@Param("id") Long id);

    int insertAdapterConfig(EaiAdapterConfig config);

    int updateAdapterConfig(EaiAdapterConfig config);

    int deleteAdapterConfig(@Param("id") Long id);
}
