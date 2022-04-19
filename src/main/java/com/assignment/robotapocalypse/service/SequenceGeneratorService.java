package com.assignment.robotapocalypse.service;

import com.assignment.robotapocalypse.dto.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService {
    @Autowired
    private MongoOperations mongoOperations;

    public String getSequenceNumber(String sequenceName) {

        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("seqNo", 1);
        DbSequence counter = mongoOperations
                .findAndModify(query, update, options().returnNew(true).upsert(true), DbSequence.class);
        return !Objects.isNull(counter) ? counter.getSeqNo() : "1";
    }

}
