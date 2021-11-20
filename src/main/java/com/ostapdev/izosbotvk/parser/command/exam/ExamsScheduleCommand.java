package com.ostapdev.izosbotvk.parser.command.exam;

import api.longpoll.bots.model.objects.basic.Message;
import com.ostapdev.izosbotvk.parser.command.BaseCommand;
import com.ostapdev.izosbotvk.service.GroupExamService;
import com.ostapdev.izosbotvk.service.PeerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamsScheduleCommand extends BaseCommand {

    private final GroupExamService examService;
    private final PeerConfigService peerConfigService;

    @Autowired
    public ExamsScheduleCommand(GroupExamService examService, PeerConfigService peerConfigService) {
        this.examService = examService;
        this.peerConfigService = peerConfigService;
    }


    @Override
    public String getCommand() {
        return "!ЭКЗАМЕНЫ";
    }

    @Override
    protected void runImpl(String message,Integer peerId) {
        examService.getAllExamsByGroup(peerId
                ,peerConfigService.getCurrentPeerConfig(peerId).getGroupNumber());
    }
}
