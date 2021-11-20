package com.ostapdev.izosbotvk.repo;

import com.ostapdev.izosbotvk.model.schedule.GroupSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface GroupScheduleRepo extends MongoRepository<GroupSchedule, BigInteger> {
    GroupSchedule getAllByGroup(String group);
}
