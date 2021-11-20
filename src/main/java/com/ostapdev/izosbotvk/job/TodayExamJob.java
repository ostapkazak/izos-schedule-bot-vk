package com.ostapdev.izosbotvk.job;

import com.ostapdev.izosbotvk.service.GroupExamService;
import com.ostapdev.izosbotvk.service.PeerConfigService;
import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodayExamJob implements Job {
    private final PeerConfigService peerConfigService;
    private final GroupExamService groupExamService;

    @Autowired
    public TodayExamJob(PeerConfigService peerConfigService, GroupExamService groupExamService) {
        this.peerConfigService = peerConfigService;
        this.groupExamService = groupExamService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        peerConfigService.getAllPeers().forEach(peerConfig ->{
            if (groupExamService.isTodayHasExam(peerConfig.getGroupNumber(), LocalDate.now().getDayOfMonth())){
                groupExamService.todayExamMessage(peerConfig.getPeerId(), peerConfig.getGroupNumber(),LocalDate.now().getDayOfMonth());
            }
        });
    }
}
