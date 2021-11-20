package com.ostapdev.izosbotvk.repo;

import com.ostapdev.izosbotvk.model.config.BotConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface BotConfigRepo extends MongoRepository<BotConfig, BigInteger> {
}
