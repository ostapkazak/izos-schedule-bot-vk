package com.ostapdev.izosbotvk.model.schedule;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Lesson {
    private String id;
    private String name;
    private String startTime;
    private String type;
    private String teacher;
    private boolean isDistance;
    private String link;
    private String place;
    private String week;

    public Lesson(String name, String startTime, String type, String teacher, boolean isDistance, String link, String place, String week) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.startTime = startTime;
        this.type = type;
        this.teacher = teacher;
        this.isDistance = isDistance;
        this.link = link;
        this.place = place;
        this.week = week;
    }
}
