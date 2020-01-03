package org.ionedan.ufortnight.website.dto;

import lombok.Getter;
import org.ionedan.ufortnight.website.entity.Author;
import org.ionedan.ufortnight.website.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel {

    @Getter
    private long id;
    @Getter
    private String title;
    @Getter
    private String subtitle;
    @Getter
    private List<Author> authors;

    public BookViewModel(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.subtitle = book.getSubtitle();
        this.authors = new ArrayList<>(book.getAuthors());
    }

    public BookViewModel() {

    }
}
