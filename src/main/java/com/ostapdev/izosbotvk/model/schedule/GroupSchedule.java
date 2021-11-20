package com.ostapdev.izosbotvk.model.schedule;

import com.ostapdev.izosbotvk.model.schedule.Day;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "group_shedule")
public class GroupSchedule {
    @Id
    private BigInteger id;

    private String group;

    private String course;

    private List<Day> days;
}
