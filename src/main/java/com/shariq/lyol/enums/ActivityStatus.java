package com.shariq.lyol.enums;

import lombok.Getter;

public enum ActivityStatus {
    TODO("TODO"),
    STARTED("STARTED"),
    COMPLETED("COMPLETED"),
    PROCRASTINATED("PROCASTINATED"),
    DID_NOT_DO("DID_NOT_DO")
    ;

    @Getter
    String name;
    ActivityStatus(String name) {
        this.name = name;
    }

    public static ActivityStatus getKey(String value) {
        try {
            if(value.isBlank()|| value.isEmpty())
                return ActivityStatus.TODO;
            return ActivityStatus.valueOf(value);
        } catch (Exception e){
            return ActivityStatus.TODO;
        }
    }
}
