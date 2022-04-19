package com.assignment.robotapocalypse.repository;

import com.assignment.robotapocalypse.dto.Survivor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurvivorRepository extends MongoRepository<Survivor, String> {

}
