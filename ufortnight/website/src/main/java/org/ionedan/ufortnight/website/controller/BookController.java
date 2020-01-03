package org.ionedan.ufortnight.website.controller;

import org.ionedan.ufortnight.website.component.LibraryService;
import org.ionedan.ufortnight.website.dto.BookViewModel;
import org.ionedan.ufortnight.website.dto.BooksListViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
    public String edit(@PathVariable(value = "id") long id, Model model) {
        var book = this.libraryService.fetchBookById(id);
        var editViewModel = new BookViewModel(book);

        model.addAttribute("model", editViewModel);
        return "books/edit";
    }

    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String getAddNewBookForm(Model model) {

        var newBook = new BookViewModel();

        model.addAttribute("model", newBook);
        return "books/create";
    }

    @PostMapping(value = "/create")
    public String processAddNewBookForm(@ModelAttribute BookViewModel newBook) {
        libraryService.create(newBook);
        return "redirect:/books";
    }
}
