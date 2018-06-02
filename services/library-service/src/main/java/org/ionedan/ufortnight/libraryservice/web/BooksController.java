package org.ionedan.ufortnight.libraryservice.web;

import org.ionedan.ufortnight.libraryservice.domain.models.Book;
import org.ionedan.ufortnight.libraryservice.services.BooksRepository;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
@ExposesResourceFor(Book.class)
public class BooksController {

    private final BooksRepository repository;
    private final EntityLinks entityLinks;

    public BooksController(BooksRepository repository, EntityLinks entityLinks) {
        Assert.notNull(repository, "repository should not be null");
        Assert.notNull(entityLinks, "entityLinks should not be null");

        this.repository = repository;
        this.entityLinks = entityLinks;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity<Resources<Book>> fetchAllBooks() {
        var resources = new Resources<>(this.repository.findAll());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity<Resource<Book>> fetchBookById(@PathVariable(name = "id") long id) {
        var book = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return new ResponseEntity<>(
                new Resource<Book>(book),
                HttpStatus.OK);
    }

}
