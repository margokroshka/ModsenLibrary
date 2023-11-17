package Library.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;
    @Value("${topic.name}")
    private String topicName;

    @Bean
    public ProducerFactory<String, KafkaBookIdAdditionDto> producerFactoryForObjectKafkaBookIdAdditionDtoTemplate() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, KafkaBookIdAdditionDto> objectKafkaBookIdAdditionDtoTemplate() {
        return new KafkaTemplate<>(producerFactoryForObjectKafkaBookIdAdditionDtoTemplate());
    }

    @Bean
    public NewTopic topicSendChangeStatusVieEmailEvent() {
        return TopicBuilder
                .name(topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
