package com.ostapdev.izosbotvk.repo;

import com.ostapdev.izosbotvk.model.exam.GroupExam;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface GroupExamRepo extends MongoRepository<GroupExam, BigInteger> {
    Optional<GroupExam> findByGroup(String group);
    List<GroupExam> findAllByOrderByCourse();
}
