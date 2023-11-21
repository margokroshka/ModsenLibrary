package LibraryService.domain.kafka;

import LibraryService.domain.service.BookWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Consumer {
    private final ObjectMapper objectMapper;
    private final BookWorker bookWorker;
    @SneakyThrows
    @KafkaListener(topics = "${topic.name}", groupId = "${topic-group.name")
    public void consumeMessage(String message){
        KafkaBookIdAddition kafkaMailMessage =objectMapper.readValue(message, KafkaBookIdAddition.class);
        bookWorker.saveBook(kafkaMailMessage.bookId());
    }
}
