package org.ionedan.ufortnight.website.dto;

import org.ionedan.ufortnight.website.entity.Author;
import org.ionedan.ufortnight.website.entity.Book;

import java.util.List;

public class BookEditViewModel {

    private String title;
    private String subtitle;
    private List<Author> authors;

    public BookEditViewModel(Book book) {

    }
}
