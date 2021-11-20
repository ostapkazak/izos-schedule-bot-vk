package com.ostapdev.izosbotvk;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.model.events.messages.MessageNewEvent;
import com.ostapdev.izosbotvk.parser.CommandParser;
import com.ostapdev.izosbotvk.service.BotConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BotImpl extends LongPollBot {

    private final BotConfigService botConfigService;
    private final CommandParser commandParser;

    @Autowired
    public BotImpl(BotConfigService botConfigService, CommandParser commandParser) {
        this.botConfigService = botConfigService;
        this.commandParser = commandParser;
    }

    @Override
    public void onMessageNew(MessageNewEvent messageNewEvent) {
        try {
            if (messageNewEvent.getMessage().hasText())
            {
                commandParser.parse(messageNewEvent.getMessage());
            }
        }catch (Exception e){
            log.error("Exception:",e);;
        }
    }

    @Override
    public String getAccessToken() {
        return botConfigService.getAccessToken();
    }

    @Override
    public int getGroupId() {
        return botConfigService.getGroupId();
    }
}
