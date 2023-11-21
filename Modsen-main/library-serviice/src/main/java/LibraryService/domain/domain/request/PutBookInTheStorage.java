package LibraryService.domain.domain.request;

import java.time.LocalDate;

public record PutBookInTheStorage(LocalDate start, LocalDate end) {
}
