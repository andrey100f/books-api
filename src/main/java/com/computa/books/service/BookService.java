package com.computa.books.service;

import com.computa.books.model.Book;
import com.computa.books.model.BookDto;
import com.computa.books.model.exception.InvalidResourceException;
import com.computa.books.model.exception.ResourceNotFoundException;
import com.computa.books.model.mapper.BookMapper;
import com.computa.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing books
 *  This class provides methods for CRUD operation and custom queries on the Book entity
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * Retrieves all books from the repository
     * @return a list of BookDto objects
     */
    public List<BookDto> getAllBooks() {
        log.info("BookService - starting getAllBooks task");

        return this.bookRepository.findAll().stream()
            .map(this.bookMapper::mapToBookDto)
            .toList();
    }

    /**
     * Add a new book to the repository
     * @param book the BookDto object to be added
     * @return the added BookDto object
     * @throws InvalidResourceException if the book resource is invalid
     */
    public BookDto addBook(BookDto book) {
        log.info("BookService - starting addBook task");

        return Optional.of(book)
                .map(this.bookMapper::mapToModel)
                .map(this.bookRepository::save)
                .map(this.bookMapper::mapToBookDto)
                .orElseThrow(() -> {
                    log.error("BookService - addBook task error");
                    return new InvalidResourceException("Invalid resource!");
                });
    }

    /**
     * Updates an exising book in the repository
     * @param book the BookDto object to be updated
     * @return the updated BookDto object
     * @throws InvalidResourceException if the book resource is invalid
     */
    public BookDto updateBook(BookDto book) {
        log.info("BookService - starting updateBook task");

        return this.bookRepository.findById(book.getId())
                .map(book1 -> this.bookRepository.save(this.bookMapper.mapToModel(book)))
                .map(this.bookMapper::mapToBookDto)
                .orElseThrow(() -> {
                    log.error("BookService - updateBook task error");
                    return new InvalidResourceException("Invalid resource!");
                });
    }

    /**
     * Retrieves a book by its id
     * @param id the id of the book to be retrieved
     * @return the retrieved BookDto object
     * @throws ResourceNotFoundException if the book is not found
     */
    public BookDto getBookById(String id) {
        log.info("BookService - starting getBookById task");

        return this.bookRepository.findById(id)
                .map(this.bookMapper::mapToBookDto)
                .orElseThrow(() -> {
                    log.error("BookService - findBookById task - Book not found");
                    return new ResourceNotFoundException("Book not found!");
                });
    }

    /**
     * Deletes a book by its id
     * @param id the id of the book to be deleted
     * @throws ResourceNotFoundException if the book is not found
     */
    public void deleteBook(String id) {
        log.info("BookService - starting deleteBook task");

        this.bookRepository.findById(id)
                .map(book -> {
                    this.bookRepository.delete(book);
                    return book;
                })
                .orElseThrow(() -> {
                    log.error("BookService - deleteBook task - Book not found");
                    return new ResourceNotFoundException("Book not found!");
                });
    }

    /**
     * Retrieves all books with a year greater than the specified year
     * @param bookYear the year to compare against
     * @return a list of BookDto objects with a year greater than the specified year
     */
    public List<BookDto> getAllBookWithYearGreaterThan(Long bookYear) {
        log.info("BookService - starting getBookWithYearGreaterThan task");

        return this.bookRepository.findAllBooksWithValueGreaterThan("book_year", bookYear, Book.class)
                .stream()
                .map(this.bookMapper::mapToBookDto)
                .toList();
    }

    /**
     * Retrieves a book by its isbn
     * @param isbn the isbn of the book to be retrieved
     * @return the retrieved BookDto object
     * @throws ResourceNotFoundException if the book is not found
     */
    public BookDto getBookByIsbn(String isbn) {
        log.info("BookService - starting getBookByIsbn task");

        return this.bookRepository.findBookByField("isbn", isbn, Book.class)
                .map(this.bookMapper::mapToBookDto)
                .orElseThrow(() -> {
                    log.error("BookService - getBookByIsbn task - Book not found");
                    return new ResourceNotFoundException("Book not found");
                });
    }

}
