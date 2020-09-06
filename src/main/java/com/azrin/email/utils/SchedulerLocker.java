package com.azrin.email.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchedulerLocker {
    private static SchedulerLocker instance;
    private boolean locked;
    public static synchronized SchedulerLocker getInstance(){
        if(instance==null){
            instance= new SchedulerLocker();
            instance.setLocked(false);
        }
        return instance;
    }
}
