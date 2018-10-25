package org.ionedan.ufortnight.libraryservice.api;

import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.exceptions.AuthorNotFoundException;
import org.ionedan.ufortnight.libraryservice.services.AuthorsRepository;
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

@RestController
@RequestMapping("/api/authors")
@ExposesResourceFor(Author.class)
public class AuthorsController {

    private final AuthorsRepository repository;
    private final EntityLinks entityLinks;

    public AuthorsController(AuthorsRepository repository, EntityLinks entityLinks) {
        Assert.notNull(repository, "Address repository cannot be null");
        Assert.notNull(entityLinks, "EntityLinks cannot be null");

        this.repository = repository;
        this.entityLinks = entityLinks;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity<Resources<Author>> fetchAllAuthors() {
        var resources = new Resources<Author>(this.repository.findAll());
        return new ResponseEntity<Resources<Author>>(resources, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity<Resource<Author>> fetchAuthorById(@PathVariable Long id) {
        var author = this.repository.findById(id)
                .orElseThrow(
                        () -> new AuthorNotFoundException(String.format("Author having id '%d' not found.", id))
                );

        var resource = new Resource<Author>(author);
        return new ResponseEntity<>(resource, HttpStatus.OK);
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
