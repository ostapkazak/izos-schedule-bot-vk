package com.ostapdev.izosbotvk.job.service;

import com.ostapdev.izosbotvk.job.util.TimerUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

@Service
@Slf4j
public class SchedulerService {
    private final Scheduler scheduler;

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void scheduleTodayLessons(final Class jobClass){
        final JobDetail jobDetail = TimerUtil.buildJobDetail(jobClass);
        final Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(dailyAtHourAndMinute(7, 30))
                .build();

        try {
            scheduler.scheduleJob(jobDetail,trigger);
        }catch (SchedulerException e){
            log.error("Exception:",e);
        }
    }

    public void scheduleTodayExam(final Class jobClass){
        final JobDetail jobDetail = TimerUtil.buildJobDetail(jobClass);
        final Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(dailyAtHourAndMinute(7, 32))
                .build();

        try {
            scheduler.scheduleJob(jobDetail,trigger);
        }catch (SchedulerException e){
            log.error("Exception:",e);
        }
    }

    @PostConstruct
    public void init(){
        try {
            scheduler.start();
        }catch (SchedulerException e){
            log.error("Exception:",e);
        }
    }

    @PreDestroy
    public void preDestroy(){
        try {
            scheduler.shutdown();
        }catch (SchedulerException e){
            log.error("Exception:",e);
        }
    }
}
