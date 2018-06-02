package org.ionedan.ufortnight.libraryservice.web;

import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.domain.models.Book;
import org.ionedan.ufortnight.libraryservice.services.BooksRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Fail.fail;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class BooksControllerTests {

    @Autowired
    private BooksRepository repository;

    @Before
    public void setUp() {
        this.repository.deleteAll();

        var book1 = new Book("Book1");
        book1.setAuthors(List.of(
                new Author("firstname1", "lastname1"),
                new Author("firstname2", "lastname2")
        ));
        var book2 = new Book("Book2");
        book2.setAuthors(List.of(
            new Author("firstname21", "lastname21"),
            new Author("firstname22", "lastname22")
        ));

        repository.saveAll(List.of(book1, book2));
    }


    @Test
    public void fetchAllBooks() {
        fail("Not yet implemented");
    }

    @Test
    public void fetchById() {
        fail("Not yet implemented");
    }



}
