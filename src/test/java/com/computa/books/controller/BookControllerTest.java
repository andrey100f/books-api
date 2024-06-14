package com.computa.books.controller;

import com.computa.books.model.BookDto;
import com.computa.books.model.exception.ResourceNotFoundException;
import com.computa.books.model.mapper.BookMapper;
import com.computa.books.repository.BookRepository;
import com.computa.books.service.BookService;
import com.computa.books.utils.BooksTestUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllBooks() throws Exception {
        List<BookDto> booksFromService = BooksTestUtils.getListOfBooksDto();

        when(this.bookService.getAllBooks()).thenReturn(booksFromService);

        this.mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookById_ShouldReturnOk() throws Exception {
        BookDto bookDto = BooksTestUtils.getBookDto();

        when(this.bookService.getBookById(bookDto.getId())).thenReturn(bookDto);

        this.mockMvc.perform(get("/books/{id}", bookDto.getId())).andExpect(status().isOk());
    }

    @Test
    public void testGetBookById_ShouldThrowNoSuchElementException() throws Exception {
        String bookId = BooksTestUtils.getBookId();

        when(this.bookService.getBookById(bookId)).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(get("/books/{id}", bookId)).andExpect(status().isNotFound());
    }

    @Test
    public void testGetBooksWithYearGreaterThan_ShouldReturnOk() throws Exception {
        Long bookYear = BooksTestUtils.getBookYear();
        List<BookDto> books = BooksTestUtils.getListOfBooksDto();

        when(this.bookService.getAllBookWithYearGreaterThan(bookYear)).thenReturn(books);

        this.mockMvc.perform(get("/books/filterByYear/{bookYear}", bookYear)).andExpect(status().isOk());
    }

    @Test
    public void testAddBook_ShouldReturnOk() throws Exception {
        BookDto bookDto = BooksTestUtils.getAddBookDto();
        BookDto addedBook = BooksTestUtils.getBookDto();
        JSONObject jsonObject = BooksTestUtils.getBookJsonObject(bookDto);

        when(this.bookService.addBook(bookDto)).thenReturn(addedBook);

        this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddBook_ShouldThrowMethodArgumentNotValidException() throws Exception {
        BookDto bookDto = BooksTestUtils.getUpdateFailBookDto();
        JSONObject jsonObject = BooksTestUtils.getBookJsonObject(bookDto);

        this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBook_ShouldReturnOk() throws Exception {
        BookDto bookDto = BooksTestUtils.getBookDto();
        JSONObject jsonObject = BooksTestUtils.getBookJsonObject(bookDto);

        when(this.bookService.updateBook(bookDto)).thenReturn(bookDto);

        this.mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBook_ShouldThrowResourceNotFoundException() throws Exception {
        BookDto bookDto = BooksTestUtils.getBookDto();
        JSONObject jsonObject = BooksTestUtils.getBookJsonObject(bookDto);

        when(this.bookService.updateBook(bookDto)).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBook_ShouldThrowMethodArgumentNotValidException() throws Exception {
        BookDto bookDto = BooksTestUtils.getUpdateFailBookDto();
        JSONObject jsonObject = BooksTestUtils.getBookJsonObject(bookDto);

        this.mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteBook_ShouldReturnOk() throws Exception {
        String bookId = BooksTestUtils.getBookId();

        doNothing().when(this.bookService).deleteBook(bookId);

        this.mockMvc.perform(delete("/books/{id}", bookId)).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBook_ShouldThrowResourceNotFoundException() throws Exception {
        String bookId = BooksTestUtils.getBookId();

        doThrow(ResourceNotFoundException.class).when(this.bookService).deleteBook(bookId);

        this.mockMvc.perform(delete("/books/{id}", bookId)).andExpect(status().isNotFound());
    }

    @Test
    void testBookByIsbn_ShouldReturnOk() throws Exception {
        BookDto bookDto = BooksTestUtils.getBookDto();

        when(this.bookService.getBookByIsbn(bookDto.getIsbn())).thenReturn(bookDto);

        this.mockMvc.perform(get("/books/filterByIsbn/{isbn}", bookDto.getIsbn())).andExpect(status().isOk());
    }

    @Test
    public void testGetBookByIsbn_ShouldThrowResourceNotFoundException() throws Exception {
        String isbn = BooksTestUtils.getBookIsbn();

        when(this.bookService.getBookByIsbn(isbn)).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(get("/books/filterByIsbn/{isbn}", isbn)).andExpect(status().isNotFound());
    }

}
