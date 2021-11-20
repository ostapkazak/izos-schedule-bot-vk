package com.ostapdev.izosbotvk.service;

import api.longpoll.bots.model.objects.basic.Message;
import com.ostapdev.izosbotvk.model.config.BotConfig;
import com.ostapdev.izosbotvk.repo.BotConfigRepo;
import com.ostapdev.izosbotvk.sender.VkMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotConfigService {

    private final BotConfigRepo botConfigRepo;
    private final StringBuilder messageBuilder = new StringBuilder();

    @Autowired
    public BotConfigService(BotConfigRepo botConfigRepo) {
        this.botConfigRepo = botConfigRepo;
    }

    public BotConfig getConfig(){
        return botConfigRepo.findAll().get(0);
    }

    public String getAccessToken(){
       return getConfig().getAccessToken();
    }

    public Integer getGroupId(){
        return getConfig().getGroupId();
    }

    public String getAllCommands(){
        messageBuilder.setLength(0);

        getConfig()
                .getCommands()
                .forEach(command -> {
                    messageBuilder
                            .append(command.getName()).append(" - ").append(command.getDescription()).append("\n\n");
                });

        return messageBuilder.toString();
    }


}
