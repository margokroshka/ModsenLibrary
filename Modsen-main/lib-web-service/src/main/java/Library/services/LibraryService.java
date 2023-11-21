package Library.services;

import Library.domain.PutBookInTheStorage;
import Library.domain.BookRequest;
import Library.domain.response.BookLogResponse;
import Library.domain.response.BookResponse;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LibraryService {
    BookResponse createBook(BookRequest body);

    List<BookResponse> getAllBooks();

    BookResponse getBookById(Integer id) throws NotFoundException;

    BookResponse updateBookById(Integer id, BookRequest body) throws NotFoundException;

    void deleteById(Integer id) throws NotFoundException;
    List<BookResponse> getAllBooksByISBN(String ISBN);
    List<BookResponse>  getFreeBooks();

    ResponseEntity<BookLogResponse> changeBookStatus(Integer id, PutBookInTheStorage putBookInTheStorageDto);

}
