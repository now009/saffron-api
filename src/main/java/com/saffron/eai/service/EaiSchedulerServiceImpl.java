package com.saffron.eai.service;

import com.saffron.eai.domain.EaiSchedule;
import com.saffron.eai.mapper.EaiScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EaiSchedulerServiceImpl implements EaiSchedulerService {

    private final EaiScheduleMapper scheduleRepository;
    private final WorkflowEngine workflowEngine;

    @Override
    public List<EaiSchedule> findAll(String interfaceId) {
        return scheduleRepository.selectScheduleList(interfaceId, null);
    }

    @Override
    public EaiSchedule findById(Long id) {
        EaiSchedule schedule = scheduleRepository.selectScheduleById(id);
        if (schedule == null) {
            throw new IllegalArgumentException("스케줄을 찾을 수 없습니다: " + id);
        }
        return schedule;
    }

    @Override
    @Transactional
    public EaiSchedule save(EaiSchedule schedule) {
        if (schedule.getId() == null) {
            schedule.setCreatedAt(LocalDateTime.now());
            scheduleRepository.insertSchedule(schedule);
        } else {
            schedule.setUpdatedAt(LocalDateTime.now());
            scheduleRepository.updateSchedule(schedule);
        }
        return schedule;
    }

    @Override
    public void executeNow(Long scheduleId) {
        EaiSchedule schedule = findById(scheduleId);
        log.info("[Scheduler] 즉시 실행 scheduleId={} interfaceId={}", scheduleId, schedule.getInterfaceId());
        // TODO: WorkflowEngine으로 즉시 실행 처리
        scheduleRepository.updateLastRunAt(scheduleId, LocalDateTime.now(), "SUCCESS");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        scheduleRepository.deleteSchedule(id);
    }
}
