package com.shariq.lyol.service;

import com.shariq.lyol.models.Schedule;
import com.shariq.lyol.repositories.ScheduleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepo scheduleRepo;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepo scheduleRepo){
        this.scheduleRepo = scheduleRepo;
    }
    @Override
    public void createOrUpdateSchedule(Schedule schedule) {
        scheduleRepo.save(schedule);
    }

    @Override
    public void viewSchedule() {

    }

    @Override
    public void clearSchedule() {
        scheduleRepo.deleteById(LocalDate.now());
    }

    @Override
    public Schedule checkIfScheduleExistsOrCreateNew(){
        return scheduleRepo.findById(LocalDate.now()).orElse(Schedule.builder()
                .date(LocalDate.now())
                .activities(new ArrayList<>())
                .score(0)
                .build());
    }

}
