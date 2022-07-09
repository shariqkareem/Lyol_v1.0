package com.shariq.lyol.service;

import com.shariq.lyol.enums.ActivityStatus;
import com.shariq.lyol.models.Activity;
import com.shariq.lyol.models.Schedule;
import com.shariq.lyol.repositories.ScheduleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class DayAnalysisService {
    private final ScheduleRepo scheduleRepo;

    @Autowired
    public DayAnalysisService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public void calculateDaysScore() throws Exception{
        try {
            Schedule schedule = scheduleRepo.findByDate(LocalDate.now()).orElseThrow();
            List<Activity> activities = schedule.getActivities();
            long totalActivities = activities.size();
            long completedActivities = activities.stream()
                    .filter(activity -> activity.getActivityStatus().equals(ActivityStatus.COMPLETED))
                    .count();
            log.info("Total activities : " + totalActivities);
            log.info("Completed activities : " + completedActivities);
            DecimalFormat f = new DecimalFormat("##.##");
            float daysScore = Float.parseFloat(f.format((completedActivities * 10) / (float) totalActivities));
            schedule.setScore(daysScore);
            scheduleRepo.save(schedule);
            log.info("************* Day's score : " + daysScore + " **************");
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error when calculating day's score");
        }
    }
}
