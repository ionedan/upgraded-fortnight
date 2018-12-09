package org.ionedan.ufortnight.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode @ToString
public class Publisher {

    @Getter
    private Integer id;

    @Getter @NonNull
    private String name;
}
