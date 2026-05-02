package com.saffron.eai.scheduler;

import com.saffron.eai.domain.EaiSchedule;
import com.saffron.eai.repository.EaiScheduleRepository;
import com.saffron.eai.service.EaiSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EaiBatchScheduler {

    private final EaiScheduleRepository scheduleRepository;
    private final EaiSchedulerService schedulerService;

    @Scheduled(fixedDelay = 60000)
    public void runScheduledInterfaces() {
        List<EaiSchedule> activeSchedules = scheduleRepository.selectScheduleList(null, true);
        log.debug("[Scheduler] 활성 스케줄 {}건 확인", activeSchedules.size());

        for (EaiSchedule schedule : activeSchedules) {
            if (isDue(schedule)) {
                log.info("[Scheduler] 실행 scheduleId={} interfaceId={}", schedule.getId(), schedule.getInterfaceId());
                try {
                    schedulerService.executeNow(schedule.getId());
                } catch (Exception e) {
                    log.error("[Scheduler] 실행 실패 scheduleId={}: {}", schedule.getId(), e.getMessage());
                }
            }
        }
    }

    private boolean isDue(EaiSchedule schedule) {
        // TODO: cron 표현식 기반 실행 시점 판단 (CronExpression 활용)
        return schedule.getNextRunAt() != null &&
                !schedule.getNextRunAt().isAfter(java.time.LocalDateTime.now());
    }
}
