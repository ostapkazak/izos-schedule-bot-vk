package com.ostapdev.izosbotvk.model.peer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "peers_config ")
public class PeerConfig {
    @Id
    private BigInteger id;

    @NonNull
    private Integer peerId;

    @NonNull
    private String groupNumber;

    @Field(targetType = FieldType.STRING)
    @NonNull
    private BotState botState;

    @NonNull
    private boolean isMailing;
}
