package Library.controller;

import Library.domain.BookRequest;
import Library.domain.PutBookInTheStorage;
import Library.domain.response.BookLogResponse;
import Library.domain.response.BookResponse;
import Library.services.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksController {
    private final LibraryService libraryService;



        @Operation(
                summary = "Get all books",
                responses = {
                        @ApiResponse(responseCode = "200", description = "List of books",
                                content = @Content(schema = @Schema(implementation = BookResponse.class)))
                }
        )
        @GetMapping("/getAll")
        public ResponseEntity<List<BookResponse>> getAllBooks () {
        return ResponseEntity.ok().body(libraryService.getAllBooks());
    }


    @Operation(
            summary = "Get book by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book found",
                            content = @Content(schema = @Schema(implementation = BookResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @GetMapping("/getId/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(libraryService.getBookById(id));
    }

    @Operation(
            summary = "Get book by ISBN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of books",
                            content = @Content(schema = @Schema(implementation = BookResponse.class)))
            }
    )
    @GetMapping("/getISBN/{isbn}")
    public ResponseEntity<List<BookResponse>> getBookByISBN(@PathVariable Integer isbn) {
        return ResponseEntity.ok().body(libraryService.getAllBooksByISBN(isbn));
    }


    @Operation(
            summary = "Get free books",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of books",
                            content = @Content(schema = @Schema(implementation = BookResponse.class)))
            }
    )
    @GetMapping("/getFreeBooks")
    public ResponseEntity<List<BookResponse>> getFreeBooks() {
        return ResponseEntity.ok().body(libraryService.getFreeBooks());
    }


    @Operation(
            summary = "Create a book",
            description = "Create a new user book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Book successfully created",
                    content = @Content(schema = @Schema(implementation = BookResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest body) {
            try {
               var response = ResponseEntity.ok().body(libraryService.createBook(body));
               return response;
            }catch (Throwable e){
                e.printStackTrace();
            }
        return null;
    }

    @Operation(
            summary = "Update book by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book updated",
                            content = @Content(schema = @Schema(implementation = BookResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<BookResponse> updateBook(@RequestBody BookRequest body,  @PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(libraryService.updateBookById(id, body));
    }

    @Operation(
            summary = "Update book status by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book status updated",
                            content = @Content(schema = @Schema(implementation = BookResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<BookLogResponse> updateStatusBook(@RequestBody PutBookInTheStorage putBookInTheStorageDto, @PathVariable Integer id) throws NotFoundException {
        return libraryService.changeBookStatus(id, putBookInTheStorageDto);
    }

    @Operation(
            summary = "Delete book",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book deleted"),
                    @ApiResponse(responseCode = "404", description = "Book not found")

            }
    )
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void  deleteBook(@PathVariable Integer id)  throws NotFoundException {
       libraryService.deleteById(id);
    }
}
