package org.ionedan.ufortnight.libraryservice.web;

import org.ionedan.ufortnight.libraryservice.LibraryServiceApplication;
import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.services.AuthorsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryServiceApplication.class)
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
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$._embedded.authorList", hasSize(3)))
                .andExpect(jsonPath("$._embedded.authorList[0].firstName", is("firstName1")))
                .andExpect(jsonPath("$._embedded.authorList[0].lastName", is("lastName1")));
    }


    @Test
    public void fetchAuthorById() throws Exception {
        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                //.andExpect(content().string("{}"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("firstName1")))
                .andExpect(jsonPath("$.lastName", is("lastName1")));;
    }

}
