package Library.services;

import Library.domain.Books;
import Library.domain.PutBookInTheStorage;
import Library.endpoint.LibraryEndpoint;
import Library.domain.BookRequest;
import Library.kafka.Producer;
import Library.repository.BooksRepository;
import Library.domain.response.BookLogResponse;
import Library.domain.response.BookResponse;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements LibraryService {
    private final String BookWthIdNotFoundException = "Book with id %s not found!";
    private final BooksRepository booksRepository;
    private final Producer producer;
    private final ModelMapper modelMapper;
    private final LibraryEndpoint libraryEndpoint;
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public BookResponse createBook(BookRequest bodyBook) {
        Books books = modelMapper.map(bodyBook, Books.class);
        Books saveBook = booksRepository.save(books);
        producer.sendKafkaBookIdAdditionDtoToConsumer(saveBook.getId());
        return modelMapper.map(saveBook, BookResponse.class);
    }
    public List<BookResponse> getAllBooks() {
        return  booksRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }

    @Override
    public BookResponse getBookById(Integer id) throws NotFoundException {
        Books books =getBookByIdFromDB(id);
        return modelMapper.map(books, BookResponse.class);
    }

    @Override
    public BookResponse updateBookById(Integer id, BookRequest body) throws NotFoundException {
        return booksRepository.findById(id)
                .map(book -> {
                    Books mapBook = modelMapper.map(body, Books.class);
                    mapBook.setId(book.getId());
                    booksRepository.save(mapBook);
                    return modelMapper.map(booksRepository.save(mapBook), BookResponse.class);
                })
                .orElseThrow(() ->
                        new NotFoundException(String.format("BOOK_WITH_ID_NOT_FOUND_EXCEPTION", id))
                );
    }

    @Override
    public  void deleteById(Integer id) throws NotFoundException {
        Books books = getBookByIdFromDB(id);
        booksRepository.deleteById(id);
    }

    public List<BookResponse> getAllBooksByISBN(String isbn) {
        return booksRepository.findBookByISBN(isbn)
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }

    @Override
    public ResponseEntity<BookLogResponse> changeBookStatus(Integer id, PutBookInTheStorage putBookInTheStorageDto) {
        return libraryEndpoint.changeBookStatus(id, putBookInTheStorageDto);
    }


    private Books getBookByIdFromDB(int id) throws NotFoundException {
       return booksRepository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("Book wth id not found", id)
                ));
    }
}
