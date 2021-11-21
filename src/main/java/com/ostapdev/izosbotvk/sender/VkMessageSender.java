package com.ostapdev.izosbotvk.sender;

import api.longpoll.bots.exceptions.BotsLongPollException;
import api.longpoll.bots.methods.messages.MessagesSend;
import api.longpoll.bots.model.objects.additional.Button;
import api.longpoll.bots.model.objects.additional.Keyboard;
import api.longpoll.bots.model.objects.basic.Message;
import com.ostapdev.izosbotvk.service.BotConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class VkMessageSender {
    private final BotConfigService botConfigService;

    @Autowired
    public VkMessageSender(BotConfigService botConfigService) {
        this.botConfigService = botConfigService;
    }

    public void send(Integer peerId, String messageText) {
        try {
            new MessagesSend(botConfigService.getAccessToken())
                    .setPeerId(peerId)
                    .setMessage(messageText)
                    .setKeyboard(generateKeyboard())
                    .execute();
        }catch (BotsLongPollException e){
            log.error("Exception:",e);
        }
    }



    private Keyboard generateKeyboard(){
        List<Button> row1 = new ArrayList<>();
        List<Button> row2 = new ArrayList<>();

        row1.add(new Button()
                .setAction(new Button.TextAction().setLabel("!сегодня"))
                .setColor(Button.ButtonColor.POSITIVE));

        row1.add(new Button()
                .setAction(new Button.TextAction().setLabel("!завтра"))
                .setColor(Button.ButtonColor.POSITIVE));

        row2.add(new Button()
                .setAction(new Button.TextAction().setLabel("!расписание"))
                .setColor(Button.ButtonColor.PRIMARY));

        row2.add(new Button()
                .setAction(new Button.TextAction().setLabel("!экзамены"))
                .setColor(Button.ButtonColor.PRIMARY));

        return new Keyboard().setButtons(Arrays.asList(row1,row2));
    }
}
