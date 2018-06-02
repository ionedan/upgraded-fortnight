package org.ionedan.ufortnight.libraryservice.services;

import org.assertj.core.api.Assert;
import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Fail.fail;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorsRepositoryTests {

    @Autowired
    private AuthorsRepository repository;

    @Test
    public void createAuthorTest() {
        var author = new Author("Daniel", "Ionescu");

        var entity = repository.save(author);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isGreaterThan(-1);
    }
}
