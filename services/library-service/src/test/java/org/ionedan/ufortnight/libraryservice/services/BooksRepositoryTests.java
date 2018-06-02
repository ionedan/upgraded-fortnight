package org.ionedan.ufortnight.libraryservice.services;

import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.domain.models.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksRepositoryTests {

    @Autowired
    private BooksRepository booksRepository;

    @Test
    public void book_is_created_test() {
        var book = new Book("A book");
        var author1 = new Author("firstname1", "lastname1");
        var author2 = new Author("firstname2", "lastname2");
        List<Author> authors = new ArrayList<Author>();
        authors.add(author1);
        authors.add(author2);
        book.setAuthors(authors);

        booksRepository.save(book);

        var all = booksRepository.findAll();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getAuthors()).isNotNull();
        assertThat(all.get(0).getAuthors().size()).isEqualTo(2);
    }
}
