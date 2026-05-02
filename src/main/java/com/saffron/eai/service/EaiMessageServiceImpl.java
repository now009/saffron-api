package com.saffron.eai.service;

import com.saffron.eai.domain.EaiMessageHistory;
import com.saffron.eai.dto.response.MessageHistoryResponse;
import com.saffron.eai.mapper.EaiMessageHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EaiMessageServiceImpl implements EaiMessageService {

    private final EaiMessageHistoryMapper historyRepository;
    private final WorkflowEngine workflowEngine;

    @Override
    public List<MessageHistoryResponse> findHistory(String interfaceId, String status, String keyword) {
        return historyRepository.selectHistoryList(interfaceId, status, keyword)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MessageHistoryResponse findHistoryById(Long id) {
        EaiMessageHistory history = historyRepository.selectHistoryById(id);
        if (history == null) {
            throw new IllegalArgumentException("메시지 이력을 찾을 수 없습니다: " + id);
        }
        return toResponse(history);
    }

    @Override
    @Transactional
    public void retry(Long id) {
        EaiMessageHistory history = historyRepository.selectHistoryById(id);
        if (history == null) {
            throw new IllegalArgumentException("메시지 이력을 찾을 수 없습니다: " + id);
        }
        log.info("[EaiMessageService] 재처리 요청 id={} interfaceId={}", id, history.getInterfaceId());
        // TODO: WorkflowEngine으로 재처리 실행
        historyRepository.updateRetryCount(id, (history.getRetryCount() != null ? history.getRetryCount() : 0) + 1);
    }

    private MessageHistoryResponse toResponse(EaiMessageHistory h) {
        MessageHistoryResponse res = new MessageHistoryResponse();
        res.setId(h.getId());
        res.setMessageId(h.getMessageId());
        res.setInterfaceId(h.getInterfaceId());
        res.setDirection(h.getDirection());
        res.setSourceSystem(h.getSourceSystem());
        res.setTargetSystem(h.getTargetSystem());
        res.setStatus(h.getStatus());
        res.setMessageBody(h.getMessageBody());
        res.setResponseBody(h.getResponseBody());
        res.setErrorMessage(h.getErrorMessage());
        res.setRetryCount(h.getRetryCount());
        res.setProcessingMs(h.getProcessingMs());
        res.setIsDlq(h.getIsDlq());
        res.setCreatedAt(h.getCreatedAt());
        res.setProcessedAt(h.getProcessedAt());
        return res;
    }
}
