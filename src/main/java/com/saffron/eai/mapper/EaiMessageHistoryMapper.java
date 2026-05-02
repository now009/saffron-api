package com.saffron.eai.mapper;

import com.saffron.eai.domain.EaiMessageHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EaiMessageHistoryMapper {

    List<EaiMessageHistory> selectHistoryList(@Param("interfaceId") String interfaceId,
                                               @Param("status") String status,
                                               @Param("keyword") String keyword);

    EaiMessageHistory selectHistoryById(@Param("id") Long id);

    EaiMessageHistory selectHistoryByMessageId(@Param("messageId") String messageId);

    int insertHistory(EaiMessageHistory history);

    default int save(EaiMessageHistory history) {
        return insertHistory(history);
    }

    int updateHistoryStatus(@Param("id") Long id, @Param("status") String status);

    int updateDlqStatus(@Param("messageId") String messageId);

    int updateRetryCount(@Param("id") Long id, @Param("retryCount") Integer retryCount);
}
