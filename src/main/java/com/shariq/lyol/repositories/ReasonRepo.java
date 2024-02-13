package com.shariq.lyol.repositories;

import com.shariq.lyol.models.Activity;
import com.shariq.lyol.models.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonRepo extends JpaRepository<Reason, Integer> {
    Reason findByReasonAndBlockerAndActivity(String reason, String blocker, Activity activity);
}
