package com.computa.books.repository;

import com.computa.books.model.Book;

import java.util.List;

public interface CustomBookRepository {
    List<Book> findAllBooksWithYearGreaterThan(Long bookYear);
}
