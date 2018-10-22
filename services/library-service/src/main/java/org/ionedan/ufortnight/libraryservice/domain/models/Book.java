package org.ionedan.ufortnight.libraryservice.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
