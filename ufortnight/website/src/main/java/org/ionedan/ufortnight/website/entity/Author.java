package org.ionedan.ufortnight.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode @ToString
public class Author {

    @Getter
    private Long id;

    @Getter @Setter
    private String firstName;

    @NotNull
    @Getter @Setter
    private String lastName;

}
