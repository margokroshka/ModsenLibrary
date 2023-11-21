package Library.services;

import Library.domain.BookRequest;
import Library.domain.Books;
import Library.domain.PutBookInTheStorage;
import Library.domain.response.BookLogResponse;
import Library.domain.response.BookResponse;
import Library.domain.response.GetFreeBookResponse;
import Library.endpoint.LibraryEndpoint;
import Library.kafka.Producer;
import Library.repository.BooksRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService implements LibraryService {
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
                        new NotFoundException(String.format("Book with ID not Found ", id))
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
    public List<BookResponse> getFreeBooks() {
        List<Long> longIdsList = Objects.requireNonNull(libraryEndpoint.getIdsOfBusyBooks().getBody())
                .stream()
                .map(GetFreeBookResponse::bookId)
                .toList();
        return booksRepository.findAll()
                .stream()
                .filter(book -> !longIdsList.contains(book.getId()))
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
