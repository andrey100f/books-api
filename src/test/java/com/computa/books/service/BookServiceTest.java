package com.computa.books.service;

import com.computa.books.model.Book;
import com.computa.books.model.BookDto;
import com.computa.books.model.exception.ResourceNotFoundException;
import com.computa.books.model.mapper.BookMapper;
import com.computa.books.repository.BookRepository;
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
        Book book1 = Book.builder()
                .id("664c57b4314734178cfaac51")
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();
        List<Book> booksFromRepo = List.of(book1);

        when(this.bookRepository.findAll()).thenReturn(booksFromRepo);

        List<BookDto> books = this.bookService.getAllBooks();
        assertEquals(1, books.size());
    }

    @Test
    public void testFindBookById() {
        Book book = Book.builder()
                .id("664c57b4314734178cfaac51")
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();

        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookMapper.mapToBookDto(Mockito.any())).thenReturn(bookDto);
        when(this.bookRepository.findById("664c57b4314734178cfaac51")).thenReturn(Optional.of(book));

        BookDto bookFromService = this.bookService.getBookById("664c57b4314734178cfaac51");
        assertNotNull(bookFromService);
    }

    @Test
    public void testFindBookById_ShouldThrowNoSuchElementException() {
        String bookId = "664c57b4314734178cfaac51";

        when(this.bookRepository.findById(bookId)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> this.bookService.getBookById(bookId));
    }

    @Test
    public void testAddBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        Book bookModel = Book.builder()
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();

        Book addedBook = Book.builder()
                .id("664c57b4314734178cfaac51")
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();

        BookDto addedBookDto = new BookDto();
        addedBookDto.setId("664c57b4314734178cfaac51");
        addedBookDto.setTitle("To Kill a Mockingbird");
        addedBookDto.setAuthor("Harper Lee");
        addedBookDto.setBookYear(1960L);
        addedBookDto.setType("Fiction");
        addedBookDto.setIsbn("978-0-06-112008-4");
        addedBookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookMapper.mapToModel(addedBookDto)).thenReturn(bookModel);
        when(this.bookRepository.save(bookModel)).thenReturn(addedBook);
        when(this.bookMapper.mapToBookDto(addedBook)).thenReturn(addedBookDto);

        BookDto testedAddedBook = this.bookService.addBook(addedBookDto);
        assertEquals(testedAddedBook, addedBookDto);
    }

    @Test
    public void testUpdateBook_ShouldReturnOk() {
        Book bookToUpdate = Book.builder()
                .id("664c57b4314734178cfaac51")
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();

        Book book = Book.builder()
                .id("664c57b4314734178cfaac51")
                .title("test update title")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();

        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("test update title");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookRepository.findById("664c57b4314734178cfaac51")).thenReturn(Optional.of(bookToUpdate));
        when(this.bookMapper.mapToModel(bookDto)).thenReturn(book);
        when(this.bookRepository.save(book)).thenReturn(book);
        when(this.bookMapper.mapToBookDto(book)).thenReturn(bookDto);

        BookDto updatedBook = this.bookService.updateBook(bookDto);

        assertEquals(bookDto, updatedBook);
    }

    @Test
    public void testUpdateBook_ShouldThrowNoSuchElementException() {
        String bookId = "664c57b4314734178cfaac51";

        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("test update title");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookRepository.findById(bookId)).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> this.bookService.updateBook(bookDto));
    }

    @Test
    void testDeleteBook_ShouldReturnOk() {
        String bookId = "664c57b4314734178cfaac51";

        Book bookToDelete = Book.builder()
                .id("664c57b4314734178cfaac51")
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();

        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(bookToDelete));

        this.bookService.deleteBook(bookId);

        Mockito.verify(this.bookRepository).delete(bookToDelete);
    }

    @Test
    void testDeleteBook_ShouldThrowNoSuchElementException() {
        String bookId = "664c57b4314734178cfaac51";

        when(this.bookRepository.findById(bookId)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> this.bookService.deleteBook(bookId));
    }

    @Test
    void testGetBookByIsbn_ShouldReturnOk() {
        Book book = Book.builder()
                .id("664c57b4314734178cfaac51")
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();

        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookMapper.mapToBookDto(Mockito.any())).thenReturn(bookDto);
        when(this.bookRepository.findBookByIsbn("978-0-06-112008-4")).thenReturn(Optional.of(book));

        BookDto bookFromService = this.bookService.getBookByIsbn("978-0-06-112008-4");
        assertNotNull(bookFromService);
    }

    @Test
    void testGetBookByIsbn_ShouldThrowResourceNotFoundException() {
        String isbn = "978-0-06-112008-4";

        when(this.bookRepository.findBookByIsbn(isbn)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> this.bookService.getBookByIsbn(isbn));
    }

}
