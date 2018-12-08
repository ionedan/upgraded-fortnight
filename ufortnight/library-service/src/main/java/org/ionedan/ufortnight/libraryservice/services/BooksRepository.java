package org.ionedan.ufortnight.libraryservice.services;

import org.ionedan.ufortnight.libraryservice.domain.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQuery;
import java.util.List;


public interface BooksRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);

    /*@Query("select b from Book b where b.author_id = :authorId")*/
    List<Book> findByAuthorsId(@Param("authorId") Long authorId);

}
