package Library.repository;

import Library.domain.Books;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface BooksRepository extends JpaRepository<Books, Integer> {
    @Column(name="isbn")
    List<Books> findBookByIsbn(Integer isbn);
}
