package Library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PutBookInTheStorage (
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate start,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate end
){}



