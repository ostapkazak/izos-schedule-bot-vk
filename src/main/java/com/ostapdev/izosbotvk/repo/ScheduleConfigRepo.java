package com.ostapdev.izosbotvk.repo;

import com.ostapdev.izosbotvk.model.schedule.ScheduleConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface ScheduleConfigRepo extends MongoRepository<ScheduleConfig, BigInteger> {
}
