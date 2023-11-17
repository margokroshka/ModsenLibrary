package Library.repository;

import Library.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
    List<Books> findBookByISBN (String isbn);
}
