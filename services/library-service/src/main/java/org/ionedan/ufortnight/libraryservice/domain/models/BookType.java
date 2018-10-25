package org.ionedan.ufortnight.libraryservice.domain.models;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode
@ToString
public class BookType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(unique = true, nullable = false)
    @Getter @Setter @lombok.NonNull
    private String name;
}
