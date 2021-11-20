package com.ostapdev.izosbotvk.parser.command.config;

import api.longpoll.bots.model.objects.basic.Message;
import com.ostapdev.izosbotvk.parser.command.BaseCommand;
import com.ostapdev.izosbotvk.service.PeerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetGroupCommand extends BaseCommand {

    private final PeerConfigService peerConfigService;

    @Autowired
    public SetGroupCommand(PeerConfigService peerConfigService) {
        this.peerConfigService = peerConfigService;
    }

    @Override
    public String getCommand() {
        return "!ГРУППА";
    }

    @Override
    protected void runImpl(String message,Integer peerId) {
        if (message.equals(getCommand())){
            peerConfigService.getPeerGroupNumber(peerId);
        }
        else {
            peerConfigService.updateCurrentGroup(peerId
                    ,message.replace(getCommand(),"").trim());
        }
    }
}
