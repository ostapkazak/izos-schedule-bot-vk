package com.ostapdev.izosbotvk;

import api.longpoll.bots.BotsLongPoll;
import api.longpoll.bots.exceptions.BotsLongPollException;
import com.ostapdev.izosbotvk.job.TodayExamJob;
import com.ostapdev.izosbotvk.job.TodayScheduleJob;
import com.ostapdev.izosbotvk.job.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Component
@Slf4j
public class BotInit {

    private final BotImpl botImpl;
    private final SchedulerService schedulerService;
    private final TodayScheduleJob todayScheduleJob;
    private final TodayExamJob todayExamJob;

    @Autowired
    public BotInit(BotImpl botImpl, SchedulerService schedulerService, TodayScheduleJob todayScheduleJob, TodayExamJob todayExamJob) {
        this.botImpl = botImpl;
        this.schedulerService = schedulerService;
        this.todayScheduleJob = todayScheduleJob;
        this.todayExamJob = todayExamJob;
    }

    @EventListener({ApplicationReadyEvent.class})
    public void init() throws UnknownHostException {
            schedulerService.scheduleTodayLessons(todayScheduleJob.getClass());
            schedulerService.scheduleTodayExam(todayExamJob.getClass());

            try {
                new BotsLongPoll(botImpl).run();
            } catch (BotsLongPollException e) {
                log.error("Exception:",e);
            }
    }
}
