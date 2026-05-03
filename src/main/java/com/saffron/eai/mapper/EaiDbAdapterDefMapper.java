package com.saffron.eai.mapper;

import com.saffron.eai.domain.EaiDbAdapterDef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EaiDbAdapterDefMapper {

    List<EaiDbAdapterDef> selectDbAdapterList(@Param("dbType") String dbType,
                                               @Param("direction") String direction);

    EaiDbAdapterDef selectDbAdapterById(@Param("id") Long id);

    EaiDbAdapterDef selectDbAdapterByAdapterId(@Param("dbAdapterId") String dbAdapterId);

    int insertDbAdapter(EaiDbAdapterDef dbAdapterDef);

    int updateDbAdapter(EaiDbAdapterDef dbAdapterDef);

    int deleteDbAdapter(@Param("id") Long id);
}
