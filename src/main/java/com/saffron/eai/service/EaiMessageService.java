package com.saffron.eai.service;

import com.saffron.eai.dto.response.MessageHistoryResponse;

import java.util.List;

public interface EaiMessageService {

    List<MessageHistoryResponse> findHistory(String interfaceId, String status, String keyword);

    MessageHistoryResponse findHistoryById(Long id);

    void retry(Long id);
}
