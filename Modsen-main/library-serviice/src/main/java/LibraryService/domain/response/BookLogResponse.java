package LibraryService.domain.response;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record BookLogResponse(Integer id, LocalDate starts, LocalDate end) {
}
