package org.ionedan.ufortnight.libraryservice.web;

import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.domain.models.Book;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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

        var book1 = Book.builder()
                .title("Book1")
                .authors(List.of(
                    new Author("firstname1", "lastname1"),
                    new Author("firstname2", "lastname2")))
                .build();

        var book2 = Book.builder()
                .title("Book2")
                .authors(List.of(
                    new Author("firstname21", "lastname21"),
                    new Author("firstname22", "lastname22")))
                .build();

        repository.saveAll(List.of(book1, book2));

        this.mockMvc = webAppContextSetup(webApplicationContext)
                .build();
    }


    @Test
    public void fetchAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType(mediaType)))
                //.andExpect(content().string("blabla "))
                .andExpect(jsonPath("$._embedded.bookList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.bookList[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.bookList[0].title", is("Book1")))
                .andExpect(jsonPath("$._embedded.bookList[0].authors", hasSize(2)))
                .andExpect(jsonPath("$._embedded.bookList[0].authors[0].firstName", is("firstname1")))
                .andExpect(jsonPath("$._embedded.bookList[0].authors[0].lastName", is("lastname1")))
                //.andExpect(jsonPath("$._embedded.bookList[1].id", is(2)))
                .andExpect(jsonPath("$._embedded.bookList[1].title", is("Book2")));
    }

    @Test
    public void fetchById() throws Exception {
        int id = 1;
        mockMvc.perform(get("/api/books/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType(mediaType)))
                //.andExpect(content().string("blabla"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Book1")))
                .andExpect(jsonPath("$.authors", hasSize(2)))
                .andExpect(jsonPath("$.authors[0].id", is(2)))
                .andExpect(jsonPath("$.authors[0].firstName", is("firstname1")))
                .andExpect(jsonPath("$.authors[0].lastName", is("lastname1")));
        /*
        * Expected :blabla
Actual   :{"id":1,"title":"Book1","subtitle":null,"description":null,"authors":[{"id":2,"firstName":"firstname1","lastName":"lastname1"},{"id":3,"firstName":"firstname2","lastName":"lastname2"}],"yearOfAppearance":null}*/
    }


    @Test
    public void createBook() throws Exception {
        var newBook = Book.builder()
                .title("A new book")
                .authors(List.of(new Author("newAuthorFirstName", "newAuthorLastName")))
                .build();

        mockMvc.perform(
                post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(asJsonString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("A new book")))
                .andExpect(jsonPath("$.authors", hasSize(1)))
                .andExpect(jsonPath("$.authors[0].firstName", is("newAuthorFirstName")));
    }

    @Test
    public void deleteBook() throws Exception {
        // arrange and act
        var newBook = this.repository.save(
                Book.builder()
                        .authors(
                                List.of(
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
    }

    @Test
    public void updateBook() throws Exception {
        // arrange and act
        var newBook = this.repository.save(
                Book.builder()
                        .authors(
                                List.of(
                                        Author.builder()
                                                .firstName("newAuthorFirstName")
                                                .lastName("newAuthorLastName")
                                                .build()))
                        .title("deleteMe")
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
