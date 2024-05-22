package com.computa.books.controller;

import com.computa.books.model.BookDto;
import com.computa.books.model.exception.ResourceNotFoundException;
import com.computa.books.model.mapper.BookMapper;
import com.computa.books.repository.BookRepository;
import com.computa.books.service.BookService;
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
        BookDto book1 = new BookDto();
        book1.setId("664c57b4314734178cfaac51");
        book1.setTitle("To Kill a Mockingbird");
        book1.setAuthor("Harper Lee");
        book1.setBookYear(1960L);
        book1.setType("Fiction");
        book1.setIsbn("978-0-06-112008-4");
        book1.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        List<BookDto> booksFromService = List.of(book1);

        when(this.bookService.getAllBooks()).thenReturn(booksFromService);

        this.mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookById_ShouldReturnOk() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookService.getBookById(bookDto.getId())).thenReturn(bookDto);

        this.mockMvc.perform(get("/books/{id}", bookDto.getId())).andExpect(status().isOk());
    }

    @Test
    public void testGetBookById_ShouldThrowNoSuchElementException() throws Exception {
        String bookId = "664c57b4314734178cfaac51";

        when(this.bookService.getBookById(bookId)).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(get("/books/{id}", bookId)).andExpect(status().isNotFound());
    }

    @Test
    public void testGetBooksWithYearGreaterThan_ShouldReturnOk() throws Exception {
        Long bookYear = 1900L;

        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("1984");
        bookDto.setAuthor("George Orwel");
        bookDto.setBookYear(1949L);

        List<BookDto> books = List.of(bookDto);

        when(this.bookService.getAllBookWithYearGreaterThan(bookYear)).thenReturn(books);

        this.mockMvc.perform(get("/books/filterByYear/{bookYear}", bookYear)).andExpect(status().isOk());
    }

    @Test
    public void testAddBook_ShouldReturnOk() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        BookDto addedBook = new BookDto();
        addedBook.setId("664c57b4314734178cfaac51");
        addedBook.setTitle("To Kill a Mockingbird");
        addedBook.setAuthor("Harper Lee");
        addedBook.setBookYear(1960L);
        addedBook.setType("Fiction");
        addedBook.setIsbn("978-0-06-112008-4");
        addedBook.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookService.addBook(bookDto)).thenReturn(addedBook);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", bookDto.getId());
        jsonObject.put("title", bookDto.getTitle());
        jsonObject.put("author", bookDto.getAuthor());
        jsonObject.put("bookYear", bookDto.getBookYear());
        jsonObject.put("type", bookDto.getType());
        jsonObject.put("isbn", bookDto.getIsbn());
        jsonObject.put("description", bookDto.getDescription());

        this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddBook_ShouldThrowMethodArgumentNotValidException() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", bookDto.getId());
        jsonObject.put("title", bookDto.getTitle());
        jsonObject.put("author", bookDto.getAuthor());
        jsonObject.put("bookYear", bookDto.getBookYear());
        jsonObject.put("type", bookDto.getType());
        jsonObject.put("isbn", bookDto.getIsbn());
        jsonObject.put("description", bookDto.getDescription());

        this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBook_ShouldReturnOk() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookService.updateBook(bookDto)).thenReturn(bookDto);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "664c57b4314734178cfaac51");
        jsonObject.put("title", bookDto.getTitle());
        jsonObject.put("author", bookDto.getAuthor());
        jsonObject.put("bookYear", bookDto.getBookYear());
        jsonObject.put("type", bookDto.getType());
        jsonObject.put("isbn", bookDto.getIsbn());
        jsonObject.put("description", bookDto.getDescription());

        this.mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBook_ShouldThrowResourceNotFoundException() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookService.updateBook(bookDto)).thenThrow(ResourceNotFoundException.class);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "664c57b4314734178cfaac51");
        jsonObject.put("title", bookDto.getTitle());
        jsonObject.put("author", bookDto.getAuthor());
        jsonObject.put("bookYear", bookDto.getBookYear());
        jsonObject.put("type", bookDto.getType());
        jsonObject.put("isbn", bookDto.getIsbn());
        jsonObject.put("description", bookDto.getDescription());

        this.mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBook_ShouldThrowMethodArgumentNotFoundException() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "664c57b4314734178cfaac51");
        jsonObject.put("title", bookDto.getTitle());
        jsonObject.put("author", bookDto.getAuthor());
        jsonObject.put("bookYear", bookDto.getBookYear());
        jsonObject.put("type", bookDto.getType());
        jsonObject.put("isbn", bookDto.getIsbn());
        jsonObject.put("description", bookDto.getDescription());

        this.mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteBook_ShouldReturnOk() throws Exception {
        String bookId = "664c57b4314734178cfaac51";

        doNothing().when(this.bookService).deleteBook(bookId);

        this.mockMvc.perform(delete("/books/{id}", bookId)).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBook_ShouldThrowResourceNotFoundException() throws Exception {
        String bookId = "664c57b4314734178cfaac51";

        doThrow(ResourceNotFoundException.class).when(this.bookService).deleteBook(bookId);

        this.mockMvc.perform(delete("/books/{id}", bookId)).andExpect(status().isNotFound());
    }

    @Test
    void testBookByIsbn_ShouldReturnOk() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        when(this.bookService.getBookByIsbn(bookDto.getIsbn())).thenReturn(bookDto);

        this.mockMvc.perform(get("/books/filterByIsbn/{isbn}", bookDto.getIsbn())).andExpect(status().isOk());
    }

    @Test
    public void testGetBookByIsbn_ShouldThrowResourceNotFoundException() throws Exception {
        String isbn = "978-0-06-112008-4";

        when(this.bookService.getBookByIsbn(isbn)).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(get("/books/filterByIsbn/{isbn}", isbn)).andExpect(status().isNotFound());
    }

}
