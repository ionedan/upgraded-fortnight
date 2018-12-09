package org.ionedan.ufortnight.libraryservice.api;

import org.ionedan.ufortnight.libraryservice.domain.models.*;
import org.ionedan.ufortnight.libraryservice.services.AuthorsRepository;
import org.ionedan.ufortnight.libraryservice.services.BooksRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.ionedan.ufortnight.libraryservice.JsonUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class BooksControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private BooksRepository repository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String mediaType = "application/json;charset=UTF-8";

    @Before
    public void setUp() {
        this.repository.deleteAll();
        this.authorsRepository.deleteAll();

        this.mockMvc = webAppContextSetup(webApplicationContext)
                .build();
    }


    @Test
    public void fetchAllBooks() throws Exception {
        var book1 = Book.builder()
                .title("Book1")
                .authors(Set.of(
                        Author.builder().firstName("firstname11").lastName("lastname11").build(),
                        Author.builder().firstName("firstname12").lastName("lastname12").build()))
                .build();

        var book2 = Book.builder()
                .title("Book2")
                .authors(Set.of(
                        Author.builder().firstName("firstname21").lastName("lastname21").build(),
                        Author.builder().firstName("firstname22").lastName("lastname22").build()))
                .build();

        repository.saveAll(List.of(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType(mediaType)))
                //.andExpect(content().string("blabla "))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.title=='Book1')]", hasSize(1)))
                .andExpect(jsonPath("$[?(@.title=='Book2')]", hasSize(1)))
                .andExpect(jsonPath("$..authors", hasSize(2)))
                .andExpect(jsonPath("$..authors[?(@.firstName=='firstname11')]", hasSize(1)))
                .andExpect(jsonPath("$..authors[?(@.lastName=='lastname12')]", hasSize(1)));
    }

    @Test
    public void fetchById() throws Exception {
        var book1 = Book.builder()
                .title("Book1")
                .authors(Set.of(
                        Author.builder().firstName("firstname11").lastName("lastname11").build(),
                        Author.builder().firstName("firstname12").lastName("lastname12").build()))
                .languages(Set.of(
                        new Language("english"),
                        new Language("romanian")))
                .categories(Set.of(
                        new BookCategory("software"),
                        new BookCategory("design patterns")))
                .publisher(new Publisher("O'Reilly"))
                .type(new BookType(BookTypes.PrintPaperback.toString()))
                .build();

        var book2 = Book.builder()
                .title("Book2")
                .authors(Set.of(
                        Author.builder().firstName("firstname21").lastName("lastname21").build(),
                        Author.builder().firstName("firstname22").lastName("lastname22").build()))
                .build();

        repository.saveAll(List.of(book1, book2));

        int id = 1;
        mockMvc.perform(get("/api/books/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType(mediaType)))
                //.andExpect(content().string("blabla"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Book1")))
                .andExpect(jsonPath("$.authors", hasSize(2)))
                .andExpect(jsonPath("$.authors[?(@.firstName=='firstname11')]", hasSize(1)))
                .andExpect(jsonPath("$.authors[?(@.firstName=='firstname12')]", hasSize(1)));
        /*
        * Expected :blabla
Actual   :{"id":1,"title":"Book1","subtitle":null,"description":null,"authors":[{"id":2,"firstName":"firstname1","lastName":"lastname1"},{"id":3,"firstName":"firstname2","lastName":"lastname2"}],"yearOfAppearance":null}*/
    }


    @Test
    public void createBook() throws Exception {
        var newBook = Book.builder()
                .title("A new book")
                .authors(
                        Set.of(
                                Author.builder()
                                        .firstName("newAuthorFirstName")
                                        .lastName("newAuthorLastName")
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
                .build();

        mockMvc.perform(
                post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(asJsonString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("A book")))
                .andExpect(jsonPath("$.authors", hasSize(1)))
                .andExpect(jsonPath("$.authors[?(@.firstName=='newAuthorFirstName')]", hasSize(1)));
    }

    @Test
    public void deleteBook() throws Exception {
        // arrange and act
        var newBook = this.repository.save(
                Book.builder()
                        .authors(
                                Set.of(
                                        Author.builder()
                                                .firstName("newAuthorFirstName")
                                                .lastName("newAuthorLastName")
                                                .build()))
                        .title("deleteMe")
                        .build());

        Assert.notNull(newBook, "The test book was not created");

        mockMvc.perform(
                    delete("/api/books/{id}", newBook.getId()))
                .andExpect(status().isOk());

        var deletedBook = this.repository.findById(newBook.getId());
        assertThat(deletedBook).isEmpty();

        assertThat(this.authorsRepository.count()).isEqualTo(1);
    }

    @Test
    public void updateBook() throws Exception {
        // arrange and act
        var newBook = this.repository.save(
                Book.builder()
                        .authors(
                                Set.of(
                                        Author.builder()
                                                .firstName("newAuthorFirstName")
                                                .lastName("newAuthorLastName")
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

        Assert.notNull(newBook, "The test book was not created");

        final var newTitle = "Update Test: Title";
        final var newSubtitle = "Update Test: Subtitle";
        newBook.setSubtitle(newSubtitle);
        newBook.setTitle(newTitle);

        mockMvc.perform(
                    put("/api/books/{id}", newBook.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(asJsonString(newBook)))
                .andExpect(status().isOk());

        this.repository.findById(newBook.getId())
                .ifPresentOrElse(
                        b -> {
                            assertThat(b.getId()).isEqualTo(newBook.getId());
                            assertThat(b.getTitle()).isEqualTo(newTitle);
                            assertThat(b.getSubtitle()).isEqualTo(newSubtitle);
                        },
                        () -> fail("The book was not found in repository")
                );

    }

    @Test
    public void bookNotFound() {
        fail("Not yet implemented");
    }

}
