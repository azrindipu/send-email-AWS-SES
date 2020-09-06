package com.azrin.email.repository;

import com.azrin.email.Model.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(HistoryRepository.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public Page<History> getPagableHistory(Pageable pageable){
        logger.info("Getting history from database");
        Criteria criteria = new Criteria();
        Query query=new Query(criteria).with(pageable);
        List<History> histories = mongoTemplate.find(query, History.class);
        Page<History> page = PageableExecutionUtils.getPage(
                histories,
                pageable,
                () -> mongoTemplate.count(query, History.class));
        return page;
    }
}
