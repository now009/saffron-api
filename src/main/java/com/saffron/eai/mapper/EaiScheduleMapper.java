package com.saffron.eai.mapper;

import com.saffron.eai.domain.EaiSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EaiScheduleMapper {

    List<EaiSchedule> selectScheduleList(@Param("interfaceId") String interfaceId,
                                          @Param("isActive") Boolean isActive);

    EaiSchedule selectScheduleById(@Param("id") Long id);

    EaiSchedule selectScheduleByInterfaceId(@Param("interfaceId") String interfaceId);

    int insertSchedule(EaiSchedule schedule);

    int updateSchedule(EaiSchedule schedule);

    int updateLastRunAt(@Param("id") Long id,
                        @Param("lastRunAt") LocalDateTime lastRunAt,
                        @Param("lastRunStatus") String lastRunStatus);

    int deleteSchedule(@Param("id") Long id);
}
