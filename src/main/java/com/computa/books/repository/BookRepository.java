package com.computa.books.repository;

import com.computa.books.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Book entity
 * This interface extends MongoRepository to provide CRUD operations and CustomBookRepository for custom queries
 */
@Repository
public interface BookRepository extends MongoRepository<Book, String>, CustomBookRepository {
}
