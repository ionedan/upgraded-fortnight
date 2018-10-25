package org.ionedan.ufortnight.libraryservice.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode @ToString
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(unique = true) @NotNull
    @Getter @lombok.NonNull
    private String name;
}
