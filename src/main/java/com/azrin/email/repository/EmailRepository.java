package com.azrin.email.repository;

import com.azrin.email.Model.EmailModel;
import com.azrin.email.Model.History;
import com.azrin.email.utils.Constants;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailRepository {
    private static final Logger logger = LoggerFactory.getLogger(EmailRepository.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public EmailModel saveEmail( EmailModel emailModel){
        logger.info("Saving email model start");
        EmailModel savedEmailModel = mongoTemplate.save(emailModel);
        logger.info("Saved");
        return savedEmailModel;
    }

    public DeleteResult deleteEmail(String id){
        logger.info("Deleting email model start");
        Query query=new Query();
        query.addCriteria(Criteria.where(Constants._ID).is(new ObjectId(id)));
        DeleteResult deleteResult = mongoTemplate.remove(query, EmailModel.class);
        logger.info("Deleted");
        return deleteResult;
    }

    public List<EmailModel> getEmails(int limit){
        logger.info("List of email model getting start");
        Query query=new Query().limit(limit);
        List<EmailModel> emailModels = mongoTemplate.find(query, EmailModel.class);
        logger.info("Got");
        return emailModels;
    }

    public History saveHistory(History history){
        logger.info("Saving history start");
        History savedHistory = mongoTemplate.save(history);
        logger.info("Saved");
        return savedHistory;
    }
}
