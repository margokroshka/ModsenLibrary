package Library.kafka;

import lombok.Builder;

@Builder
public record KafkaBookIdAdditionDto (
    long bookId)
{
}
