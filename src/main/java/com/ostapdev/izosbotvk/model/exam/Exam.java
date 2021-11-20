package com.ostapdev.izosbotvk.model.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document
public class Exam {
    @Id
    private String id;

    private String name;

    private DateTime startAt;

    private String teacher;

    private String place;
}
