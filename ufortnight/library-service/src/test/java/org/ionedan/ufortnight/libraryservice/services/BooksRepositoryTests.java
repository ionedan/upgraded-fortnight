package org.ionedan.ufortnight.libraryservice.services;

import org.ionedan.ufortnight.libraryservice.domain.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        this.authorRepository.deleteAll();
    }

    @Test
    public void book_is_created_test() {
        booksRepository.save(
                Book.builder()
                    .authors(
                            Set.of(
                                    Author.builder()
                                            .firstName("firstname1")
                                            .lastName("lastname1")
                                            .build(),
                                    Author.builder()
                                            .firstName("firstname2")
                                            .lastName("lastname2")
                                            .build()))
                    .languages(Set.of(
                            new Language("english"),
                            new Language("romanian")))
                    .title("A book")
                    .categories(Set.of(
                            new BookCategory("software"),
                            new BookCategory("design patterns")))
                    .publisher(new Publisher("O'Reilly"))
                    .type(new BookType(BookTypes.PrintPaperback.toString()))
                    .build());

        var all = booksRepository.findAll();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(1);

        var book = all.get(0);
        assertThat(book.getAuthors()).isNotNull();
        assertThat(book.getAuthors().size()).isEqualTo(2);
        assertThat(book.getLanguages().size()).isEqualTo(2);
        assertThat(book.getType().getName()).isEqualTo(BookTypes.PrintPaperback.toString());
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
                        Book.builder()
                                .title("book1")
                                .authors(
                                        Set.of(
                                                Author.builder()
                                                        .firstName("author1_fn")
                                                        .lastName("author1_ln")
                                                        .build()))
                                .build(),
                        Book.builder()
                                .title("book2")
                                .authors(
                                        Set.of(
                                                Author.builder()
                                                        .firstName("author2_fn")
                                                        .lastName("author2_ln")
                                                        .build()))
                                .build()));

        var books = this.booksRepository.findByAuthorsId(1L);

        // assert
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("book1");
        assertThat(books.get(0).getAuthors()).hasSize(1);
    }
}
