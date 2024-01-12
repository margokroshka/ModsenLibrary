package Library.domain.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookLogResponse(
    LocalDate start,
    LocalDate end
) {
}
