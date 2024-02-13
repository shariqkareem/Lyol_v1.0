package com.shariq.lyol.service;

import com.shariq.lyol.models.Activity;
import com.shariq.lyol.models.Reason;

public interface ReasonService {
    Reason checkIfReasonExistsOrCreateNew(String reason, String blocker, Activity activity);
}
