package com.computa.books.config;

import com.computa.books.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Configuration class for setting the MongoDB database
 * This class handles the creation of indexes and the insertion and removal of the sample data
 */
@Component
@RequiredArgsConstructor
public class DatabaseConfig {

    private final MongoTemplate mongoTemplate;

    /**
     * Creates indexes in the MongoDB collection for the Book class
     */
    @PostConstruct
    public void createIndexes() {
        this.mongoTemplate.indexOps(Book.class)
                .ensureIndex(new Index().on("book_year", Sort.Direction.ASC));
    }

    /**
     * Inserts sample data in the MongoDB collection for the Book class
     */
    @PostConstruct
    public void insertData() {
        for(int i = 0; i < 100; i++) {
            Book book = Book.builder()
                    .title("titlu" + i)
                    .author("autor" + i)
                    .bookYear(2024L)
                    .type("tip" + i)
                    .description("descriere" + i)
                    .isbn("isbn" + i)
                    .build();

            this.mongoTemplate.insert(book);
        }
    }

    /**
     * Removes the sample data from the MongoDB collection for the Book class
     */
    @PreDestroy
    public void removeData() {
        for(int i = 0; i < 100; i++) {
            Query query = new Query();
            query.addCriteria(Criteria.where("title").is("titlu" + i));

            this.mongoTemplate.remove(query, Book.class);
        }

        this.mongoTemplate.indexOps(Book.class).dropIndex("book_year_1");
    }

}
