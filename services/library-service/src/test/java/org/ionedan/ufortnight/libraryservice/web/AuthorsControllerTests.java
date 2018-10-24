package org.ionedan.ufortnight.libraryservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ionedan.ufortnight.libraryservice.LibraryServiceApplication;
import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.services.AuthorsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.ionedan.ufortnight.libraryservice.JsonUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryServiceApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class AuthorsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private AuthorsRepository repository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .build();

        repository.deleteAll();

        repository.save(new Author("firstName1", "lastName1"));
        repository.save(new Author("firstName2", "lastName2"));
        repository.save(new Author("firstName3", "lastName3"));
    }

    @Test
    public void fetchAllAuthors() throws Exception {
        // arrange
        //act
        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$._embedded.authorList", hasSize(3)))
                .andExpect(jsonPath("$._embedded.authorList[0].firstName", is("firstName1")))
                .andExpect(jsonPath("$._embedded.authorList[0].lastName", is("lastName1")));
    }


    @Test
    public void fetchAuthorById() throws Exception {
        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                //.andExpect(content().string("{}"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("firstName1")))
                .andExpect(jsonPath("$.lastName", is("lastName1")));
    }

    @Test
    public void createAuthor() throws Exception {
        // https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/#unit-test-http-post
        final var firstName = "testCreationFirstName";
        final var lastName = "testCreationLastName";
        final var expectedId = 4;

        var newAuthor = new Author();
        newAuthor.setFirstName(firstName);
        newAuthor.setLastName(lastName);

        mockMvc.perform(
                post("/api/authors")
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .content(asJsonString(newAuthor))

                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType(("application/json;charset=UTF-8"))))
                .andExpect(jsonPath("$.id", is(expectedId)))
                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andExpect(jsonPath("$.lastName", is(lastName)));
    }

    @Test
    public void updateAuthor() {
        final var newFirstName = "firstName_1_1";

        repository.findById(1L)
                .ifPresentOrElse(
                        author -> {
                            author.setFirstName(newFirstName);
                            try {
                                mockMvc.perform(
                                        put("/api/authors/{id}", author.getId())
                                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                .content(asJsonString(author)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(jsonPath("$.firstName", is(newFirstName)));
                            } catch (Exception e) {
                                fail("An exception occurred", e);
                            } },
                () -> fail("Author not found in the database!"));
    }

    @Test
    public void deleteAuthor() throws Exception {
        var newAuthor = new Author();
        newAuthor.setFirstName("firstName_DELETE_ME");
        newAuthor.setLastName("lastName_DELETE_ME");

        var authorToBeDeleted = repository.save(newAuthor);
        mockMvc.perform(
                delete("/api/authors/{id}", authorToBeDeleted.getId()))
        .andExpect(status().isOk());

        repository.findById(authorToBeDeleted.getId())
                .ifPresent(
                        (author) -> fail(String.format("Author having id '%d' was not removed from the storage.", author.getId()))

                );
    }

    @Test
    public void authorNotFound() {
        fail("Not yet implemented");
    }




}
