package Library.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class BookResponse {
    private Long id;
    private String name;
    private String genre;
    private String description;
    private String authorName;
    private String ISBN;

}
