package LibraryService.service;

import LibraryService.domain.BookLog;
import LibraryService.domain.request.PutBookInTheStorage;
import LibraryService.domain.response.BookLogResponse;
import LibraryService.domain.response.GetFreeBookResponse;
import LibraryService.repository.BookLogRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookWorkerService implements BookWorker {
    private final BookLogRepository bookLogRepository;

    @Override
    public void saveBook(Integer id) {
        BookLog newLog = BookLog.builder().bookId(id).build();
        bookLogRepository.save(newLog);
    }

    @Override
    public List<GetFreeBookResponse> getFreeBooks() {
        return bookLogRepository.findAll()
                .stream()
                .filter(bookLog -> Objects.nonNull(bookLog.getStartTime()) && Objects.isNull(bookLog.getEndTime()))
                .map(bookLog -> GetFreeBookResponse.builder()
                        .bookId(bookLog.getBookId())
                        .build()
                )
                .toList();
    }

    @Override
    public BookLogResponse updateBookInTheLibrary(Integer bookId, PutBookInTheStorage putBookInTheStorageDto) throws Exception {
        BookLog bookLog = bookLogRepository.findBookLogById(bookId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("BookLog with ID not Found ", bookId))
                );
        BookLog returnBookLog = null;

        if (Objects.nonNull(putBookInTheStorageDto.start())) {
            if (Objects.nonNull(bookLog.getStartTime()) && Objects.nonNull(bookLog.getEndTime())) {
                bookLog.setEndTime(null);
                bookLog.setStartTime(putBookInTheStorageDto.start());
                returnBookLog = bookLogRepository.save(bookLog);
            } else if (Objects.isNull(bookLog.getStartTime()) && Objects.isNull(bookLog.getEndTime())) {
                bookLog.setStartTime(putBookInTheStorageDto.start());
                returnBookLog = bookLogRepository.save(bookLog);
            } else {
                throw new Exception("Book has already taken exception");
            }
        } else if (Objects.nonNull(putBookInTheStorageDto.end())) {
            bookLog.setEndTime(putBookInTheStorageDto.end());
            returnBookLog = bookLogRepository.save(bookLog);
        }
        return BookLogResponse.builder()
                .id(returnBookLog.getBookId())
                .end(returnBookLog.getEndTime())
                .starts(returnBookLog.getStartTime())
                .build();
    }
}
