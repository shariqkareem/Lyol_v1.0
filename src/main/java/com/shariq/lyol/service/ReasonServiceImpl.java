package com.shariq.lyol.service;

import com.shariq.lyol.models.Activity;
import com.shariq.lyol.models.Reason;
import com.shariq.lyol.repositories.ReasonRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReasonServiceImpl implements ReasonService{
    private final ReasonRepo reasonRepo;

    @Autowired
    public ReasonServiceImpl(ReasonRepo reasonRepo) {
        this.reasonRepo = reasonRepo;
    }

    @Override
    public Reason checkIfReasonExistsOrCreateNew(String reason, String blocker, Activity activity) {
        return reasonRepo.findByReasonAndBlockerAndActivity(reason, blocker, activity);
    }
}
