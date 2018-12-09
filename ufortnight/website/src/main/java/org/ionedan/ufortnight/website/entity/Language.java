package org.ionedan.ufortnight.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode @ToString
public class Language {

    @Getter
    private Integer id;

    @Getter @NonNull
    private String name;

    // TODO: add language ISO code https://www.loc.gov/standards/iso639-2/php/code_list.php
}
