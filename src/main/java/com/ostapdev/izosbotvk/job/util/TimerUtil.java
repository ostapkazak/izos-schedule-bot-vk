package com.ostapdev.izosbotvk.job.util;

import org.quartz.*;

public final class TimerUtil {
    private TimerUtil(){}

    public static JobDetail buildJobDetail(final Class jobClass){
        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .build();
    }

    public static Trigger buildTrigger(final Class jobClass){
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        simpleScheduleBuilder.repeatForever();
        return TriggerBuilder
                .newTrigger()
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
