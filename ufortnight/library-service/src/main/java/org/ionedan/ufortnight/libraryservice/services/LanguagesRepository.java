package org.ionedan.ufortnight.libraryservice.services;

import org.ionedan.ufortnight.libraryservice.domain.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguagesRepository extends JpaRepository<Language, Integer> {
}
