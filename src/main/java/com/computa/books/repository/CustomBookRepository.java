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
     * Retrieves a book by its field
     * @param key field name
     * @param value field value
     * @param tClass the class type of the object searched
     * @return the retrieved object of type S
     * @param <T> the type of the given value
     * @param <S> the type of the searched objects
     */
    <T, S> Optional<S> findBookByField(String key, T value, Class<S> tClass);

    /**
     * Retrieves a list of books with a field value greater than a specified input
     * @param key field name
     * @param value field value
     * @param tClass the class type of the object searched
     * @return a list of objects of type S
     * @param <T> the type of the given value
     * @param <S> the type of the searched objects
     */
    <T, S> List<S> findAllBooksWithValueGreaterThan(String key, T value, Class<S> tClass);
}