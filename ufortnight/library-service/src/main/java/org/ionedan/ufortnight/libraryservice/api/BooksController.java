package org.ionedan.ufortnight.libraryservice.api;

import org.ionedan.ufortnight.libraryservice.domain.models.Book;
import org.ionedan.ufortnight.libraryservice.exceptions.BookNotFoundException;
import org.ionedan.ufortnight.libraryservice.services.BooksRepository;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/books")
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpEntity<Resources<Book>> fetchAllBooks() {
        var resources = new Resources<Book>(this.repository.findAll());
        return new ResponseEntity<Resources<Book>>(resources, HttpStatus.OK);
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpEntity<Resource<Book>> fetchBookById(@PathVariable long id) {
        var book = this.repository.findById(id)
                .orElseThrow(
                        () -> new BookNotFoundException(
                                String.format("Book having the id '%d' was not found", id))
                );

        return new ResponseEntity<>(
                new Resource<Book>(book),
                HttpStatus.OK);
    }


    @PostMapping()
    Book create(@RequestBody Book newBook) {
        return this.repository.save(newBook);
    }


    @PutMapping("/{id}")
    Book update(@RequestBody Book newBook, @PathVariable long id) {
        return repository.findById(id)
                .map(
                        book -> {
                            book.setAuthors(newBook.getAuthors());
                            book.setCategories(newBook.getCategories());
                            book.setDescription(newBook.getDescription());
                            book.setLanguages(newBook.getLanguages());
                            book.setPublisher(newBook.getPublisher());
                            book.setSubtitle(newBook.getSubtitle());
                            book.setTitle(newBook.getTitle());
                            book.setType(newBook.getType());
                            book.setYearOfAppearance(newBook.getYearOfAppearance());

                            return this.repository.save(book);
                        }
                )
                .orElseThrow(
                        () -> new BookNotFoundException(
                                String.format("Book having the id '%d' was not found", id))
                );
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable  Long id) {
        repository.findById(id)
                .ifPresentOrElse(
                        this.repository::delete,
                        () -> new BookNotFoundException(
                                String.format("Book having the id '%d' was not found", id))
                );
    }

}
