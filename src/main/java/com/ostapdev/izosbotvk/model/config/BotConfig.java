package com.ostapdev.izosbotvk.model.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "bot_config")
public class BotConfig {
    @Id
    private BigInteger id;

    private Integer groupId;

    private String accessToken;

    @Field(name = "commands")
    private List<Command> commands;

    private List<Command> adminCommands;
}
