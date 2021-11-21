package com.ostapdev.izosbotvk.parser;

import api.longpoll.bots.model.objects.basic.Message;
import com.ostapdev.izosbotvk.model.peer.BotState;
import com.ostapdev.izosbotvk.parser.command.BaseCommand;
import com.ostapdev.izosbotvk.sender.VkMessageSender;
import com.ostapdev.izosbotvk.service.PeerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandParser {
    private final List<BaseCommand> baseCommands;
    private final VkMessageSender vkMessageSender;
    private final PeerConfigService peerConfigService;

    @Autowired
    public CommandParser(List<BaseCommand> baseCommands, VkMessageSender vkMessageSender, PeerConfigService peerConfigService) {
        this.baseCommands = baseCommands;
        this.vkMessageSender = vkMessageSender;
        this.peerConfigService = peerConfigService;
    }

    public void parse(Message message){
        if (peerConfigService.getCurrentPeerConfig(message.getPeerId()) == null){
            if (message.getPeerId().equals(message.getFromId())) vkMessageSender
                    .send(message.getPeerId(),"Я работаю только в беседах групп кафедры ИЗОС");
            else {
                peerConfigService.addNewPeerConfig(message.getPeerId(),"", BotState.DEFAULT,true);
                vkMessageSender
                        .send(message.getPeerId()
                                ,"Приветствую!\n\nУзнать список доступных команд бота - !помощь" +
                                        "\nУстановить группу для беседы - !группа [номер]");
            }

            return;
        }
        for (BaseCommand command : baseCommands){
            command.run(message);
        }
    }
}
