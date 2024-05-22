package com.computa.books.model.mapper;

import com.computa.books.model.Book;
import com.computa.books.model.BookDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto mapToBookDto(Book book);
    Book mapToModel(BookDto bookDto);
    List<BookDto> mapToDtoList(List<Book> books);
}
