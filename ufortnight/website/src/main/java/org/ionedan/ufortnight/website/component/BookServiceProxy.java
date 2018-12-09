package org.ionedan.ufortnight.website.component;

import org.ionedan.ufortnight.website.entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="library-service", path = "http://localhost:8060/api/books")
public interface BookServiceProxy {
    @RequestMapping(value="/", method= RequestMethod.GET)
    List<Book> fetchAllBooks();
}
