package com.saffron.eai.mapper;

import com.saffron.eai.domain.EaiDbAdapterConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EaiDbAdapterConfigMapper {

    List<EaiDbAdapterConfig> selectDbAdapterConfigList(@Param("interfaceId") String interfaceId,
                                                        @Param("datasourceId") String datasourceId);

    EaiDbAdapterConfig selectDbAdapterConfigById(@Param("id") Long id);

    int insertDbAdapterConfig(EaiDbAdapterConfig config);

    int updateDbAdapterConfig(EaiDbAdapterConfig config);

    int deleteDbAdapterConfig(@Param("id") Long id);
}
