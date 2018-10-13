package org.ionedan.ufortnight.libraryservice.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Setter @Getter
    private String title;

    @Setter @Getter
    private String subtitle;

    @Setter @Getter
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Setter @Getter
    private List<Author> authors;

    @Setter @Getter
    private Integer yearOfAppearance;

    public Book() {

    }

    public Book(String title) {
        this.title = title;
    }

}
