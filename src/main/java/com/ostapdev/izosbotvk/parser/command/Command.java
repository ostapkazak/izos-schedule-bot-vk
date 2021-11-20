package com.ostapdev.izosbotvk.parser.command;

import api.longpoll.bots.model.objects.basic.Message;

public interface Command {
    void run(Message message);
}
