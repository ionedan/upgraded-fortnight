package org.ionedan.ufortnight.libraryservice.services;

import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.domain.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksRepositoryTests {

    @Autowired
    private AuthorsRepository authorRepository;

    @Autowired
    private BooksRepository booksRepository;


    @Before
    public void setUp() {
        this.booksRepository.deleteAll();
    }

    @Test
    public void book_is_created_test() {
        var book = Book.builder()
                .title("A book")
                .authors(
                        List.of(
                                new Author("firstname1", "lastname1"),
                                new Author("firstname2", "lastname2")))
                .build();

        booksRepository.save(book);

        var all = booksRepository.findAll();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getAuthors()).isNotNull();
        assertThat(all.get(0).getAuthors().size()).isEqualTo(2);
    }

    @Test
    public void findByTitle() {
        // arrange
        final var title = "A book";
        var book = Book.builder()
                .title(title)
                .build();

        booksRepository.save(book);

        // act
        var books = this.booksRepository.findByTitle(title);

        // assert
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo(title);
    }


    @Test
    public void findByAuthorId() {
        // arrange
        this.booksRepository.saveAll(
                List.of(
                        Book.builder().title("book1").authors(List.of(new Author("author1_fn", "author1_ln"))).build(),
                        Book.builder().title("book2").authors(List.of(new Author("author2_fn", "author2_ln"))).build()));

        var books = this.booksRepository.findByAuthorsId(1L);

        // assert
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("book1");
        assertThat(books.get(0).getAuthors()).hasSize(1);
        assertThat(books.get(0).getAuthors().get(0).getFirstName()).isEqualTo("author1_fn");
    }
}
