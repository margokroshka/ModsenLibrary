package Library.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Producer {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, KafkaBookIdAdditionDto> kafkaBookIdAdditionDtoKafkaTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());



    @SneakyThrows
    public void sendKafkaBookIdAdditionDtoToConsumer(Integer id) {
        KafkaBookIdAdditionDto kafkaChangeStatusDto = KafkaBookIdAdditionDto.builder()
                .bookId(id)
                .build();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        String bookId = objectMapper.writeValueAsString(kafkaChangeStatusDto);
        kafkaBookIdAdditionDtoKafkaTemplate.send("book.addition.id.notification", kafkaChangeStatusDto);
        log.info("send book addition id notification to consumer {}", bookId);
    }
}
