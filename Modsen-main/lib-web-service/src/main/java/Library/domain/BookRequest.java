package Library.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private String ISBN;
    private String name;
    private String genre;
    private String description;
    private String Author;
}
