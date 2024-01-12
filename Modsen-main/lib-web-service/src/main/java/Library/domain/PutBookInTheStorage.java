package Library.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PutBookInTheStorage (
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate start,
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate end
){}



