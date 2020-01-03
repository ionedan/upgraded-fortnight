package org.ionedan.ufortnight.libraryservice.config;

import org.ionedan.ufortnight.libraryservice.domain.models.*;
import org.ionedan.ufortnight.libraryservice.services.BooksRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Set;

@Configuration
@Profile("dev")
public class LoadDatabase {

    @Bean
    CommandLineRunner initBooks(BooksRepository repository)  {
        return args -> {
            repository.saveAll(
                    List.of(Book.builder()
                                    .title("Book1")
                                    .authors(Set.of(
                                            Author.builder().firstName("firstname11").lastName("lastname11").build(),
                                            Author.builder().firstName("firstname12").lastName("lastname12").build()))
                                    .languages(Set.of(
                                            new Language("english"),
                                            new Language("romanian")))
                                    .categories(Set.of(
                                            new BookCategory("software"),
                                            new BookCategory("design patterns")))
                                    .publisher(new Publisher("O'Reilly"))
                                    .type(new BookType(BookTypes.PrintPaperback.toString()))
                                    .build(),
                            Book.builder()
                                    .title("Book2")
                                    .authors(Set.of(
                                            Author.builder().firstName("firstname21").lastName("lastname21").build(),
                                            Author.builder().firstName("firstname22").lastName("lastname22").build()))
                                    .build()
                    )
            );
        };
    }
}
