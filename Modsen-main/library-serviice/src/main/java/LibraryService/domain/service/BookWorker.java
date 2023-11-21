package LibraryService.domain.service;

import LibraryService.domain.domain.request.PutBookInTheStorage;
import LibraryService.domain.domain.response.BookLogResponse;
import LibraryService.domain.domain.response.GetFreeBookResponse;

import java.util.List;

public interface BookWorker {
    void saveBook(Integer id);
List<GetFreeBookResponse> getFreeBooks();
    BookLogResponse updateBookInTheLibrary(Integer bookId, PutBookInTheStorage putBookInTheStorageDto) throws Exception;

}
