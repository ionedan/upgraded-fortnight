package org.ionedan.ufortnight.website.component;

import org.ionedan.ufortnight.website.dto.BookViewModel;
import org.ionedan.ufortnight.website.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LibraryService {
    private static final Logger logger = LoggerFactory.getLogger(LibraryService.class);

    private RestTemplate restTemplate;

    @Value("${library-service.url}")
    private String libraryServiceUrl;

    private final String bookUrl = "/books";

    @Autowired
    public LibraryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Book> fetchAllBooks() {
        // https://gist.github.com/ripla/6f1516e3d0c28f4d591303d4060342d4
        final var  url = libraryServiceUrl + bookUrl;
        Map<String, Integer> params = new HashMap<>();
        /*params.put("page", offset / limit);
        params.put("size", limit);*/

        final var response = restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Book>>() {
                        }, params);

        if (response.getStatusCode() == HttpStatus.OK) {

            logger.info(response.toString());

            return response.getBody()
                    .stream()
                    //.map(Resource::getContent)
                    .collect(Collectors.toList());
        }

        return new ArrayList<Book>();
    }

    public Book fetchBookById(long id) {
        final var url = this.libraryServiceUrl + bookUrl;

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        final var response = this.restTemplate.exchange(
                url
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<Book>() {}
                , params);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // TODO: throws a customer error to let know user that the book was not found.
            return null;
        }
    }

    public void create(BookViewModel newBook) {
    }
}
