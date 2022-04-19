package com.assignment.robotapocalypse.repository;

import com.assignment.robotapocalypse.dto.ReportInfected;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportInfectedRepository extends MongoRepository<ReportInfected, String> {
    List<ReportInfected> findByInfectedSurvivorId(String infectedSurvivorId);
}
