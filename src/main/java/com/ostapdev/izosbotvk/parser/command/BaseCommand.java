package com.ostapdev.izosbotvk.parser.command;


import api.longpoll.bots.model.objects.basic.Message;

import java.util.Locale;

public abstract class BaseCommand implements Command {

    public abstract String getCommand();

    public final void run(Message message) {
        if (isCommand(message)){
            runImpl(message.getText().replace("[club205052316|@izos_bot] ","")
                    .toUpperCase(Locale.ROOT), message.getPeerId() );
        }
    }

    abstract protected void runImpl(String message, Integer peerId);

    private boolean isCommand(Message message){
        return message.getText().replace("[club205052316|@izos_bot] ","")
                .toUpperCase(Locale.ROOT).startsWith(getCommand());
    }
}
