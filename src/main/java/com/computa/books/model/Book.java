package com.computa.books.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Represents a book entity stored  in the MongoDB collection
 */
@Document(collection = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "author")
    private String author;

    @Field(name = "book_year")
    private Long bookYear;

    @Field(name = "type")
    private String type;

    @Field(name = "isbn")
    private String isbn;

    @Field(name = "description")
    private String description;

}
