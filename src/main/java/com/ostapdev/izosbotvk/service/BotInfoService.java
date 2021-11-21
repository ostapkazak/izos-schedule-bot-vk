package com.ostapdev.izosbotvk.service;

import com.ostapdev.izosbotvk.sender.VkMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotInfoService {
    private final BotConfigService botConfigService;
    private final VkMessageSender messageSender;

    @Autowired
    public BotInfoService(BotConfigService botConfigService, VkMessageSender messageSender) {
        this.botConfigService = botConfigService;
        this.messageSender = messageSender;
    }

    public void getAllCommands(Integer peerId){
        messageSender.send(peerId, botConfigService.getAllCommands());
    }
}
