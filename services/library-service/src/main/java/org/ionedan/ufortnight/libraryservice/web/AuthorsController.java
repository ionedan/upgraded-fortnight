package org.ionedan.ufortnight.libraryservice.web;

import org.ionedan.ufortnight.libraryservice.domain.models.Author;
import org.ionedan.ufortnight.libraryservice.services.AuthorsRepository;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.util.Assert;

@Controller
@RequestMapping("/authors")
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
        var resources = new Resources<>(this.repository.findAll());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity<Resource<Author>> fetchAuthorById(@PathVariable Long id) {
        var author = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found."));

        var resource = new Resource<Author>(author);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


}
