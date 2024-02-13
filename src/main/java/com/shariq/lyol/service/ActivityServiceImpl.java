package com.shariq.lyol.service;

import com.shariq.lyol.models.Activity;
import com.shariq.lyol.repositories.ActivityRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepo activityRepo;

    public ActivityServiceImpl(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    @Override
    public Activity getActivityIfExistsOrCreateNew(String activity, LocalTime startTime, LocalTime endTime) {
        return activityRepo.findByActivityAndStartTimeAndEndTime(activity, startTime, endTime).orElse(new Activity());
    }
}
