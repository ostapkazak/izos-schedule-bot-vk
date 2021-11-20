package com.ostapdev.izosbotvk.repo;

import com.ostapdev.izosbotvk.model.peer.PeerConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface PeerConfigRepo extends MongoRepository<PeerConfig, BigInteger> {
    PeerConfig getAllByPeerId(Integer peerId);
}
