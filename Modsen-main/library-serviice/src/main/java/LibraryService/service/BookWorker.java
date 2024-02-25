package LibraryService.service;

import LibraryService.domain.request.PutBookInTheStorage;
import LibraryService.domain.response.BookLogResponse;
import LibraryService.domain.response.GetFreeBookResponse;

import java.util.List;

public interface BookWorker {
    void saveBook(Integer id);

    List<GetFreeBookResponse> getFreeBooks();

    BookLogResponse updateBookInTheLibrary(Integer bookId, PutBookInTheStorage putBookInTheStorageDto) throws Exception;

}
