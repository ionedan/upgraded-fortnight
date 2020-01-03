package org.ionedan.ufortnight.libraryservice.api;

import org.ionedan.ufortnight.libraryservice.domain.models.Book;
import org.ionedan.ufortnight.libraryservice.exceptions.BookNotFoundException;
import org.ionedan.ufortnight.libraryservice.services.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/books")
public class BooksController {

    private static final Logger logger = LoggerFactory.getLogger(BooksController.class);

    private final BooksRepository repository;

    public BooksController(BooksRepository repository) {
        Assert.notNull(repository, "repository should not be null");

        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Book> fetchAllBooks() {
        logger.info("Fetching all books.. ");
        var books = this.repository.findAll();
        logger.info("Fetched " + books.size() + " books.");

        return books;
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Book fetchBookById(@PathVariable long id) {
        return this.repository.findById(id)
                .orElseThrow(
                        () -> new BookNotFoundException(
                                String.format("Book having the id '%d' was not found", id))
                );
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
                        () -> {
                            throw new BookNotFoundException(
                                    String.format("Book having the id '%d' was not found", id));
                        }
                );
    }

}
