package Library.services;

import Library.domain.Books;
import Library.repository.BooksRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
public class BookService {
    private final BooksRepository booksRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }



    public Books getBooksById(int id) {
        try {
            Books books = booksRepository.findById(id).get();
            if (books == null) {
                throw new NoSuchElementException();
            } else {
                return books;
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new Books();
    }

    public ArrayList<Books> getAllBooks() {
        return (ArrayList<Books>) booksRepository.findAll();
    }

    public boolean updateBookByAdmin(Books book) {
        if (booksRepository.findBookByISBN(book.getISBN()) != null) {
            Books booksOfList = (Books) booksRepository.findBookByISBN(book.getISBN());
            book.setName(book.getName());
            book.setId(booksOfList.getId());
            booksRepository.saveAndFlush(book);
            return true;
        }
        return false;
    }


    public boolean deleteUser() {
            booksRepository.deleteAll();
            return true;
    }


    public boolean deleteUserById(int id) {
        if (booksRepository.findById(id).isPresent()) {
            booksRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Books> getUserByLogin() {
        return booksRepository.findBookByISBN(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
