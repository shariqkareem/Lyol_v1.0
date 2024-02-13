package com.shariq.lyol.service;

import com.shariq.lyol.models.Schedule;

public interface ScheduleService {
    void createOrUpdateSchedule(Schedule schedule) throws Exception;
    void viewSchedule() throws Exception;
    void clearSchedule();

    Schedule checkIfScheduleExistsOrCreateNew();
}
