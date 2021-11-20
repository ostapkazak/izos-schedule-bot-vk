package com.ostapdev.izosbotvk.job;

import com.ostapdev.izosbotvk.repo.BotConfigRepo;
import com.ostapdev.izosbotvk.service.GroupScheduleService;
import com.ostapdev.izosbotvk.service.PeerConfigService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodayScheduleJob implements Job {

    private final GroupScheduleService groupScheduleService;
    private final PeerConfigService peerConfigService;

    @Autowired
    public TodayScheduleJob(GroupScheduleService groupScheduleService, PeerConfigService peerConfigService) {
        this.groupScheduleService = groupScheduleService;
        this.peerConfigService = peerConfigService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        peerConfigService.getAllPeers().forEach(peerConfig ->{
            if (peerConfig.isMailing()){
                groupScheduleService.getDailyScheduleByGroup(peerConfig.getPeerId(),peerConfig.getGroupNumber(),true);
            }
        });
    }
}
