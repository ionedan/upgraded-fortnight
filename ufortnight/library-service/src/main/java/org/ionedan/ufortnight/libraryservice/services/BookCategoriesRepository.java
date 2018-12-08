package org.ionedan.ufortnight.libraryservice.services;

import org.ionedan.ufortnight.libraryservice.domain.models.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoriesRepository extends JpaRepository<BookCategory, Integer> {
}
