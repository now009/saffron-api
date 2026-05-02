package com.saffron.eai.service;

import com.saffron.eai.domain.EaiSchedule;
import com.saffron.eai.dto.response.InterfaceResponse;

import java.util.List;

public interface EaiSchedulerService {

    List<EaiSchedule> findAll(String interfaceId);

    EaiSchedule findById(Long id);

    EaiSchedule save(EaiSchedule schedule);

    void executeNow(Long scheduleId);

    void delete(Long id);
}
