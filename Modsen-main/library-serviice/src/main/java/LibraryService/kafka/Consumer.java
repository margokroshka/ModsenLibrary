package LibraryService.kafka;

import LibraryService.service.BookWorker;
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
    @KafkaListener(topics = "${application.yml:32}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message){
        KafkaBookIdAddition kafkaMailMessage =objectMapper.readValue(message, KafkaBookIdAddition.class);
        bookWorker.saveBook(kafkaMailMessage.bookId());
    }
}
