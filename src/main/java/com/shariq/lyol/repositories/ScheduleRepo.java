package com.shariq.lyol.repositories;

import com.shariq.lyol.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {
    Schedule findByDate(LocalDate date);
}
