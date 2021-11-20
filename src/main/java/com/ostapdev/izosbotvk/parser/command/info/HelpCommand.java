package com.ostapdev.izosbotvk.parser.command.info;

import com.ostapdev.izosbotvk.parser.command.BaseCommand;
import com.ostapdev.izosbotvk.service.BotInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends BaseCommand {

    private final BotInfoService service;

    @Autowired
    public HelpCommand(BotInfoService service) {
        this.service = service;
    }

    @Override
    public String getCommand() {
        return "!ПОМОЩЬ";
    }

    @Override
    protected void runImpl(String message,Integer peerId) {
        service.getAllCommands(peerId);
    }
}
