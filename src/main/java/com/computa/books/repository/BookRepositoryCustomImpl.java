package com.computa.books.repository;

import com.computa.books.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements CustomBookRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Book> findAllBooksWithYearGreaterThan(Long bookYear) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bookYear").gt(bookYear));

        return this.mongoTemplate.find(query, Book.class);
    }

}
