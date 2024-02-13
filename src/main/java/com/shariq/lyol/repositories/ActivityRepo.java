package com.shariq.lyol.repositories;

import com.shariq.lyol.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Integer> {
    Optional<Activity> findByActivityAndStartTimeAndEndTime(String activity, LocalTime startTime, LocalTime endTime);
}
