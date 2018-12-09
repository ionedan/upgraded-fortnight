package org.ionedan.ufortnight.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Book {
    @Setter
    @Getter
    private Set<Author> authors;

    @Setter
    @Getter
    private Set<BookCategory> categories;

    @Setter
    @Getter
    private String description;

    @Getter
    private Long id;

    @Getter
    @Setter
    private Set<Language> languages;

    @Getter
    @Setter
    private Publisher publisher;

    @Setter
    @Getter
    private String subtitle;

    @Getter
    @Setter
    private String title;

    @Getter @Setter
    private BookType type;

    @Setter
    @Getter
    private Integer yearOfAppearance;

}
