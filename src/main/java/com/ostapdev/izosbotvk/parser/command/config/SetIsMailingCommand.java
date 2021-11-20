package com.ostapdev.izosbotvk.parser.command.config;

import api.longpoll.bots.model.objects.basic.Message;
import com.ostapdev.izosbotvk.parser.command.BaseCommand;
import com.ostapdev.izosbotvk.service.PeerConfigService;
import org.springframework.stereotype.Component;

@Component
public class SetIsMailingCommand extends BaseCommand {

    private final PeerConfigService service;

    public SetIsMailingCommand(PeerConfigService service) {
        this.service = service;
    }

    @Override
    public String getCommand() {
        return "!РАССЫЛКА";
    }

    @Override
    protected void runImpl(String message,Integer peerId) {
        service.setIsMailing(peerId,!service.getIsMailing(peerId));
    }
}
