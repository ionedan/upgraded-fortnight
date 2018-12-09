package org.ionedan.ufortnight.website.entity;

import lombok.*;

@NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode
@ToString
public class BookType {
    @Getter
    private Integer id;

    @Getter @Setter @NonNull
    private String name;
}
