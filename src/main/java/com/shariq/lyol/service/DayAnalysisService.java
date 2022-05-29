package com.shariq.lyol.service;

import com.shariq.lyol.enums.ActivityStatus;
import com.shariq.lyol.models.Activity;
import com.shariq.lyol.models.Schedule;
import com.shariq.lyol.repositories.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DayAnalysisService {
    private final ScheduleRepo scheduleRepo;

    @Autowired
    public DayAnalysisService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public float calculateDaysScore() throws Exception{
        try {
            Schedule schedule = scheduleRepo.findByDate(LocalDate.now());
            List<Activity> activities = schedule.getActivities();
            float totalActivities = activities.size();
            long completedActivities = activities.stream()
                    .filter(activity -> activity.getActivityStatus().equals(ActivityStatus.COMPLETED))
                    .count();
            return (completedActivities * 10) / totalActivities;
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error when calculating day's score");
        }
    }
}
