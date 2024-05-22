package com.computa.books.repository;

import com.computa.books.model.Book;
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
    public List<Book> findAllBooksWithYearGreaterThan(Long bookYear) {
        log.info("BookRepositoryCustom - starting findAllBooksWithYearGreaterThan task");

        Query query = new Query();
        query.addCriteria(Criteria.where("bookYear").gt(bookYear));

        return this.mongoTemplate.find(query, Book.class);
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        log.info("BookRepositoryCustom - starting findBookByIsbn task");

        Query query = new Query();
        query.addCriteria(Criteria.where("isbn").is(isbn));

        return this.mongoTemplate.find(query, Book.class).stream().findFirst();
    }

}
