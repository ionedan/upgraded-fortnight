package org.ionedan.ufortnight.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode @ToString
public class BookCategory {

    @Getter
    private Integer id;

    @Getter @NonNull
    private String name;
}
