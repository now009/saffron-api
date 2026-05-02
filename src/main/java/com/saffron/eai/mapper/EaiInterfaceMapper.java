package com.saffron.eai.mapper;

import com.saffron.eai.domain.EaiAdapterConfig;
import com.saffron.eai.domain.EaiInterfaceDef;
import com.saffron.eai.domain.EaiMappingRule;
import com.saffron.eai.domain.EaiRoutingRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EaiInterfaceMapper {

    List<EaiInterfaceDef> selectInterfaceList(@Param("status") String status,
                                               @Param("adapterType") String adapterType,
                                               @Param("keyword") String keyword);

    EaiInterfaceDef selectInterfaceById(@Param("id") Long id);

    EaiInterfaceDef selectInterfaceByInterfaceId(@Param("interfaceId") String interfaceId);

    int insertInterface(EaiInterfaceDef interfaceDef);

    int updateInterface(EaiInterfaceDef interfaceDef);

    int updateInterfaceActive(@Param("id") Long id, @Param("isActive") Boolean isActive);

    int deleteInterface(@Param("id") Long id);

    List<EaiMappingRule> selectMappingRules(@Param("interfaceId") String interfaceId);

    List<EaiRoutingRule> selectRoutingRules(@Param("interfaceId") String interfaceId);

    EaiAdapterConfig selectAdapterConfigById(@Param("id") Long id);
}
