package org.ionedan.ufortnight.libraryservice.services;

import org.ionedan.ufortnight.libraryservice.domain.models.BookType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTypesRepository extends JpaRepository<BookType, Integer> {
}
