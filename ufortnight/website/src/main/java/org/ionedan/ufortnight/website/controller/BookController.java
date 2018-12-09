package org.ionedan.ufortnight.website.controller;

import feign.Feign;
import feign.slf4j.Slf4jLogger;
import org.ionedan.ufortnight.website.component.BookServiceProxy;
import org.ionedan.ufortnight.website.component.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private LibraryService libraryService;

    @Autowired
    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String list() {
        logger.info("Fetching all books..");

        var books = libraryService.fetchAllBooks();

        logger.info("Fetched " + books.size() + " books.");

        return "books/list";
    }
}
