package Library.domain.response;

import lombok.Builder;
@Builder
public record GetFreeBookResponse (
    Long bookId
) {
}
