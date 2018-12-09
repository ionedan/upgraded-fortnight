package org.ionedan.ufortnight.libraryservice.api;

import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.exceptions.AuthorNotFoundException;
import org.ionedan.ufortnight.libraryservice.services.AuthorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.Assert;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorsController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorsController.class);

    private final AuthorsRepository repository;

    public AuthorsController(AuthorsRepository repository, EntityLinks entityLinks) {
        Assert.notNull(repository, "Address repository cannot be null");
        Assert.notNull(entityLinks, "EntityLinks cannot be null");

        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Author> fetchAllAuthors() {
        return this.repository.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Author fetchAuthorById(@PathVariable Long id) {
        var author = this.repository.findById(id)
                .orElseThrow(
                        () -> new AuthorNotFoundException(String.format("Author having id '%d' not found.", id))
                );

        return author;
    }


    @PutMapping(path = "/{id}")
    Author update(@RequestBody Author newAuthor, @PathVariable Long id) {
        return this.repository.findById(id)
                .map(
                        author -> {
                            //author
                            author.setFirstName(newAuthor.getFirstName());
                            author.setLastName(newAuthor.getLastName());
                            return repository.save(author);
                        })
                .orElseThrow(
                        () -> new AuthorNotFoundException(String.format("Author having id '%d' not found.", id))
                );


    }

    @PostMapping()
    Author create(@RequestBody Author newAuthor) {
        return this.repository.save(newAuthor);
    }


    @DeleteMapping(path = "/{id}")
    void delete(@PathVariable Long id) {
        this.repository.deleteById(id);
    }


}
