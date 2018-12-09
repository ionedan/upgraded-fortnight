package org.ionedan.ufortnight.website.dto;

import lombok.Getter;
import lombok.ToString;
import org.ionedan.ufortnight.website.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

@ToString
public class BooksListViewModel {

    @Getter
    private final List<BookModel> books;

    public BooksListViewModel(List<Book> books) {
        this.books = books.stream()
                .map(b -> new BookModel(b))
                .collect(Collectors.toList());
    }


}

@ToString
class BookModel {
    @Getter
    private final Long id;
    @Getter
    private final String title;
    @Getter
    private final String authors;

    BookModel(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authors = book.getAuthors().stream()
                .map(a-> a.getFirstName() + " " + a.getLastName())
                .reduce( (s, a)-> s + ", " + a)
                .orElse("");
    }
}


