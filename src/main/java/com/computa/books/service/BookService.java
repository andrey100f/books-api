package com.computa.books.service;

import com.computa.books.model.BookDto;
import com.computa.books.model.exception.InvalidResourceException;
import com.computa.books.model.exception.ResourceNotFoundException;
import com.computa.books.model.mapper.BookMapper;
import com.computa.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> getAllBooks() {
        return this.bookRepository.findAll().stream()
            .map(this.bookMapper::mapToBookDto)
            .toList();
    }

    public BookDto addBook(BookDto book) {
        return Optional.of(book)
                .map(this.bookMapper::mapToModel)
                .map(this.bookRepository::save)
                .map(this.bookMapper::mapToBookDto)
                .orElseThrow(() -> new InvalidResourceException("Invalid resource"));
    }

    public BookDto updateBook(BookDto book) {
        return this.bookRepository.findById(book.getId())
                .map(book1 -> this.bookRepository.save(this.bookMapper.mapToModel(book)))
                .map(this.bookMapper::mapToBookDto)
                .orElseThrow(() -> new InvalidResourceException("Invalid resource"));
    }

    public BookDto getBookById(Long id) {
        return this.bookRepository.findById(id)
                .map(this.bookMapper::mapToBookDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public void deleteBook(Long id) {
        this.bookRepository.findById(id)
                .map(book -> {
                    this.bookRepository.delete(book);
                    return book;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

}
