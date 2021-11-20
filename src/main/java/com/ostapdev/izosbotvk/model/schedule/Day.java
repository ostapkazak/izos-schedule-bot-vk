package com.ostapdev.izosbotvk.model.schedule;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Day {

    @Field("id")
    private BigInteger id;
    private String name;
    private List<Lesson> lessons;
}
