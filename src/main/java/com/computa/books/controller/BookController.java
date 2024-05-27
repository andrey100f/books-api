package com.computa.books.controller;

import com.computa.books.model.BookDto;
import com.computa.books.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.computa.books.controller.BooksApi;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController implements BooksApi {

    private final BookService bookService;

    @SneakyThrows
    @Override
    public ResponseEntity<Void> addBook(BookDto bookDto) {
        log.info("BookController - starting addBook task");

        BookDto addedBook = this.bookService.addBook(bookDto);
        return ResponseEntity.created(new URI("/books/" + addedBook.getId())).build();
    }

    @Override
    public ResponseEntity<List<BookDto>> getAllBooks() {
        log.info("BookController - starting getAllBooks task");

        return ResponseEntity.ok(this.bookService.getAllBooks());
    }

    @Override
    public ResponseEntity<Void> deleteBook(String id) {
        log.info("BookController - starting deleteBook task");

        this.bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BookDto> getBookById(String id) {
        log.info("BookController - starting getBookById task");

        return ResponseEntity.ok(this.bookService.getBookById(id));
    }

    @Override
    public ResponseEntity<BookDto> updateBook(BookDto bookDto) {
        log.info("BookController - starting updateBook task");

        return ResponseEntity.ok(this.bookService.updateBook(bookDto));
    }

    @Override
    public ResponseEntity<List<BookDto>> getAllBooksWithYearsGreaterThan(Long bookYear) {
        log.info("BookController - starting getAllBooksWithYearGreaterThan task");

        return ResponseEntity.ok(this.bookService.getAllBookWithYearGreaterThan(bookYear));
    }

    @Override
    public ResponseEntity<BookDto> getBookByIsbn(String isbn) {
        log.info("BookController - starting getBookByIsbn task");

        return ResponseEntity.ok(this.bookService.getBookByIsbn(isbn));
    }

}
