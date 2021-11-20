package com.ostapdev.izosbotvk.model.schedule;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Document(collection = "group_schedule_config")
public class ScheduleConfig {
    @Id
    private BigInteger id;

    private List<String> lessonsSchedule;

    private boolean isEvenWeek;
}
