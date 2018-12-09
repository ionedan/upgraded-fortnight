package org.ionedan.ufortnight.website.controller;

import feign.Feign;
import feign.slf4j.Slf4jLogger;
import org.ionedan.ufortnight.website.component.BookServiceProxy;
import org.ionedan.ufortnight.website.component.LibraryService;
import org.ionedan.ufortnight.website.dto.BookEditViewModel;
import org.ionedan.ufortnight.website.dto.BooksListViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;


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
    public String list(Model model) {
        logger.info("Fetching all books..");

        var books = libraryService.fetchAllBooks();
        var bookListModel = new BooksListViewModel(books);

        logger.info("Fetched " + books.size() + " books.");

        model.addAttribute("model", bookListModel);


        return "books/list";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public String edit(@RequestParam(value = "id") long id, Model model) {
        var book = this.libraryService.fetchBookById(id);
        var editViewModel = new BookEditViewModel(book);

        model.addAttribute("model", editViewModel);
        return "books/edit";
    }
}
