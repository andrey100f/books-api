package com.computa.books.repository;

import com.computa.books.model.Book;
import com.computa.books.model.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Custom implementation of the BookRepository interface
 * This class provides custom query methods for the Book entity
 */
public interface CustomBookRepository {

    /**
     * Finds all books with a year greater than the specified year
     * @param bookYear the year to compare against
     * @return a list of book objects with a year greater than the specified year
     */
    List<Book> findAllBooksWithYearGreaterThan(Long bookYear);

    /**
     * Retrieves a book by its isbn
     * @param isbn the isbn of the book to be retrieved
     * @return the retrieved Book object
     */
    Optional<Book> findBookByIsbn(String isbn);

}