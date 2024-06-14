package com.computa.books.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements CustomBookRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public <T, S> Optional<S> findBookByField(String key, T value, Class<S> tClass) {
        log.info("BookRepositoryCustom - starting findAllBookByField task");

        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));

        return this.mongoTemplate.find(query, tClass).stream().findFirst();
    }

    @Override
    public <T, S> List<S> findAllBooksWithValueGreaterThan(String key, T value, Class<S> tClass) {
        log.info("BookRepositoryCustom - starting findAllBooksWithValueGreaterThan task");

        Query query = new Query();
        query.addCriteria(Criteria.where(key).gt(value));

        return this.mongoTemplate.find(query, tClass);
    }

}
