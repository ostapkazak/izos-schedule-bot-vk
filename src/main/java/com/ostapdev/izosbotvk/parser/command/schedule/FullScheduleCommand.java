package com.ostapdev.izosbotvk.parser.command.schedule;

import api.longpoll.bots.model.objects.basic.Message;
import com.ostapdev.izosbotvk.parser.command.BaseCommand;
import com.ostapdev.izosbotvk.service.GroupScheduleService;
import com.ostapdev.izosbotvk.service.PeerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FullScheduleCommand extends BaseCommand {

    private final GroupScheduleService scheduleService;
    private final PeerConfigService peerConfigService;

    @Autowired
    public FullScheduleCommand(GroupScheduleService scheduleService, PeerConfigService peerConfigService) {
        this.scheduleService = scheduleService;
        this.peerConfigService = peerConfigService;
    }


    @Override
    public String getCommand() {
        return "!РАСПИСАНИЕ";
    }

    @Override
    protected void runImpl(String message,Integer peerId) {
        scheduleService.getFullScheduleByGroup(peerId
                ,peerConfigService.getCurrentPeerConfig(peerId).getGroupNumber());
    }
}
