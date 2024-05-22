package com.computa.books.controller;

import com.computa.books.model.BookDto;
import com.computa.books.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController implements BooksApi {

    private final BookService bookService;

    @SneakyThrows
    @Override
    public ResponseEntity<Void> addBook(BookDto bookDto) {
        BookDto addedBook = this.bookService.addBook(bookDto);
        return ResponseEntity.created(new URI("/books/" + addedBook.getId())).build();
    }

    @Override
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(this.bookService.getAllBooks());
    }

    @Override
    public ResponseEntity<Void> deleteBook(String id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BookDto> getBookById(String id) {
        return ResponseEntity.ok(this.bookService.getBookById(id));
    }

    @Override
    public ResponseEntity<BookDto> updateBook(BookDto bookDto) {
        return ResponseEntity.ok(this.bookService.updateBook(bookDto));
    }

    @Override
    public ResponseEntity<List<BookDto>> getAllBooksWithYearsGreaterThan(Long bookYear) {
        return ResponseEntity.ok(this.bookService.getAllBookWithYearGreaterThan(bookYear));
    }
}
