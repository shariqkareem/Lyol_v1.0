package com.shariq.lyol.service;

import com.shariq.lyol.models.Activity;

import java.time.LocalTime;

public interface ActivityService {
    Activity getActivityIfExistsOrCreateNew(String activity, LocalTime startTime, LocalTime endTime);
}
