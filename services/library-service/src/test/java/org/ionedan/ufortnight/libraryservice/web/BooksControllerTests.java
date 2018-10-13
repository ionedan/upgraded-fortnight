package org.ionedan.ufortnight.libraryservice.web;

import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.domain.models.Book;
import org.ionedan.ufortnight.libraryservice.services.BooksRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class BooksControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private BooksRepository repository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String mediaType = "application/json;charset=UTF-8";

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

        this.mockMvc = webAppContextSetup(webApplicationContext)
            .build();
    }


    @Test
    public void fetchAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
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
        mockMvc.perform(get("/books/" + id))
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



}
