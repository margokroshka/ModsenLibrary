package LibraryService.controller;

import LibraryService.domain.request.PutBookInTheStorage;
import LibraryService.domain.response.BookLogResponse;
import LibraryService.domain.response.GetFreeBookResponse;
import LibraryService.service.BookWorker;
import lombok.RequiredArgsConstructor;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    private final BookWorker bookWorker;

    @GetMapping("/freeBook")
    public ResponseEntity<List<GetFreeBookResponse>> getFreeBooks() {
        return ResponseEntity.ok().body(bookWorker.getFreeBooks());
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<BookLogResponse> updateBookLog(@RequestBody PutBookInTheStorage putBookInTheStorage, @PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(bookWorker.updateBookInTheStorage(id, putBookInTheStorage));
    }
}
