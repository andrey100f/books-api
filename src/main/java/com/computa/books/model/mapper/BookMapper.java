package com.computa.books.model.mapper;

import com.computa.books.model.Book;
import com.computa.books.model.BookDto;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Book and BookDto objects
 * This interface uses MapStruct for automatic mapping
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * Maps a Book entity to a BookDto object
     * @param book the Book entity to be mapped
     * @return the mapped BookDto object
     */
    BookDto mapToBookDto(Book book);

    /**
     * Maps a BookDto object to a Book entity
     * @param bookDto the BookDto object to be mapped
     * @return the mapped Book entity
     */
    Book mapToModel(BookDto bookDto);

}
