package com.computa.books.service;

import com.computa.books.model.Book;
import com.computa.books.model.BookDto;
import com.computa.books.model.exception.ResourceNotFoundException;
import com.computa.books.model.mapper.BookMapper;
import com.computa.books.repository.BookRepository;
import com.computa.books.utils.BooksTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testFindAllBooks() {
        List<Book> booksFromRepo = BooksTestUtils.getListOfBooks();

        when(this.bookRepository.findAll()).thenReturn(booksFromRepo);

        List<BookDto> books = this.bookService.getAllBooks();
        assertEquals(1, books.size());
    }

    @Test
    public void testFindBookById() {
        Book book = BooksTestUtils.getBook();
        BookDto bookDto = BooksTestUtils.getBookDto();
        String bookId = BooksTestUtils.getBookId();

        when(this.bookMapper.mapToBookDto(Mockito.any())).thenReturn(bookDto);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDto bookFromService = this.bookService.getBookById(bookId);
        assertNotNull(bookFromService);
    }

    @Test
    public void testFindBookById_ShouldThrowNoSuchElementException() {
        String bookId = BooksTestUtils.getBookId();

        when(this.bookRepository.findById(bookId)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> this.bookService.getBookById(bookId));
    }

    @Test
    public void testAddBook() {
        Book bookModel = BooksTestUtils.getAddBook();
        Book addedBook = BooksTestUtils.getBook();
        BookDto addedBookDto = BooksTestUtils.getBookDto();

        when(this.bookMapper.mapToModel(addedBookDto)).thenReturn(bookModel);
        when(this.bookRepository.save(bookModel)).thenReturn(addedBook);
        when(this.bookMapper.mapToBookDto(addedBook)).thenReturn(addedBookDto);

        BookDto testedAddedBook = this.bookService.addBook(addedBookDto);
        assertEquals(testedAddedBook, addedBookDto);
    }

    @Test
    public void testUpdateBook_ShouldReturnOk() {
        Book bookToUpdate = BooksTestUtils.getBook();
        Book book = BooksTestUtils.getBook();
        BookDto bookDto = BooksTestUtils.getBookDto();
        String bookId = BooksTestUtils.getBookId();

        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(bookToUpdate));
        when(this.bookMapper.mapToModel(bookDto)).thenReturn(book);
        when(this.bookRepository.save(book)).thenReturn(book);
        when(this.bookMapper.mapToBookDto(book)).thenReturn(bookDto);

        BookDto updatedBook = this.bookService.updateBook(bookDto);

        assertEquals(bookDto, updatedBook);
    }

    @Test
    public void testUpdateBook_ShouldThrowNoSuchElementException() {
        String bookId = BooksTestUtils.getBookId();
        BookDto bookDto = BooksTestUtils.getBookDto();

        when(this.bookRepository.findById(bookId)).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> this.bookService.updateBook(bookDto));
    }

    @Test
    void testDeleteBook_ShouldReturnOk() {
        String bookId = BooksTestUtils.getBookId();
        Book bookToDelete = BooksTestUtils.getBook();

        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(bookToDelete));

        this.bookService.deleteBook(bookId);

        Mockito.verify(this.bookRepository).delete(bookToDelete);
    }

    @Test
    void testDeleteBook_ShouldThrowNoSuchElementException() {
        String bookId = BooksTestUtils.getBookId();

        when(this.bookRepository.findById(bookId)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> this.bookService.deleteBook(bookId));
    }

    @Test
    void testGetBookByIsbn_ShouldReturnOk() {
        Book book = BooksTestUtils.getBook();
        BookDto bookDto = BooksTestUtils.getBookDto();
        String bookIsbn = BooksTestUtils.getBookIsbn();

        when(this.bookMapper.mapToBookDto(Mockito.any())).thenReturn(bookDto);
        when(this.bookRepository.findBookByField("isbn", bookIsbn, Book.class)).
                thenReturn(Optional.of(book));

        BookDto bookFromService = this.bookService.getBookByIsbn(bookIsbn);
        assertNotNull(bookFromService);
    }

    @Test
    void testGetBookByIsbn_ShouldThrowResourceNotFoundException() {
        String isbn = BooksTestUtils.getBookIsbn();

        when(this.bookRepository.findBookByField("isbn", isbn, Book.class))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> this.bookService.getBookByIsbn(isbn));
    }

}
