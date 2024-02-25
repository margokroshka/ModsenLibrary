package LibraryService.repository;

import LibraryService.domain.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookLogRepository extends JpaRepository<BookLog, Integer> {
    Optional<BookLog> findBookLogById(Integer integer);
}
