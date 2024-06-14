package com.computa.books.utils;

import com.computa.books.model.Book;
import com.computa.books.model.BookDto;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BooksTestUtils {

    public static Book getBook() {
        return Book.builder()
                .id("664c57b4314734178cfaac51")
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .bookYear(1960L)
                .type("Fiction")
                .isbn("978-0-06-112008-4")
                .description("A novel about the serious issues of rape and racial inequality narrate…")
                .build();
    }

    public static BookDto getBookDto() {
        BookDto bookDto = new BookDto();

        bookDto.setId("664c57b4314734178cfaac51");
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setAuthor("Harper Lee");
        bookDto.setBookYear(1960L);
        bookDto.setType("Fiction");
        bookDto.setIsbn("978-0-06-112008-4");
        bookDto.setDescription("A novel about the serious issues of rape and racial inequality narrate…");

        return bookDto;
    }

    public static BookDto getAddBookDto() {
        BookDto bookDto = getBookDto();
        bookDto.setId(null);

        return bookDto;
    }

    public static Book getAddBook() {
        Book book = getBook();
        book.setId(null);

        return book;
    }

    public static BookDto getUpdateFailBookDto() {
        BookDto bookDto = getBookDto();
        bookDto.setTitle("");

        return bookDto;
    }

    public static JSONObject getBookJsonObject(BookDto bookDto) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", bookDto.getId());
        jsonObject.put("title", bookDto.getTitle());
        jsonObject.put("author", bookDto.getAuthor());
        jsonObject.put("bookYear", bookDto.getBookYear());
        jsonObject.put("type", bookDto.getType());
        jsonObject.put("isbn", bookDto.getIsbn());
        jsonObject.put("description", bookDto.getDescription());

        return jsonObject;
    }

    public static List<BookDto> getListOfBooksDto() {
        return List.of(getBookDto());
    }

    public static List<Book> getListOfBooks() {
        return List.of(getBook());
    }

    public static String getBookId() {
        return "664c57b4314734178cfaac51";
    }

    public static Long getBookYear() {
        return 1960L;
    }

    public static String getBookIsbn() {
        return "978-0-06-112008-4";
    }

}
