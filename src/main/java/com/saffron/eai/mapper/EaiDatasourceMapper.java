package com.saffron.eai.mapper;

import com.saffron.eai.domain.EaiDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EaiDatasourceMapper {

    List<EaiDatasource> selectDatasourceList(@Param("dbType") String dbType,
                                              @Param("isActive") Boolean isActive);

    EaiDatasource selectDatasourceById(@Param("id") Long id);

    EaiDatasource selectDatasourceByDatasourceId(@Param("datasourceId") String datasourceId);

    int insertDatasource(EaiDatasource datasource);

    int updateDatasource(EaiDatasource datasource);

    int deleteDatasource(@Param("id") Long id);
}
