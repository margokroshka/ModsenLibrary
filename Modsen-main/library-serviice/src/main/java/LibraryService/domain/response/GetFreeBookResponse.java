package LibraryService.domain.response;

import lombok.Builder;

@Builder
public record GetFreeBookResponse(Integer bookId) {
}
