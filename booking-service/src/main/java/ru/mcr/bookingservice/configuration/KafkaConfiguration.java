package ru.mcr.bookingservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.mcr.core.event.order.OrderCreatedEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    private String idempotence;

    /**
     * Создает новый топик для событий создания заказа.
     *
     * @return объект {@link NewTopic}, представляющий топик "booking-create-event-topic".
     */
    @Bean
    NewTopic bookingTopic() {
        return TopicBuilder
                .name("booking-create-event-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    /**
     * Возвращает конфигурации для Kafka продюсера.
     *
     * @return карта конфигураций для Kafka продюсера.
     */
    Map<String, Object> producerConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, idempotence);
        return config;
    }

    /**
     * Создает фабрику продюсеров для отправки сообщений типа {@link OrderCreatedEvent}.
     *
     * @return объект {@link ProducerFactory}, настроенный для отправки сообщений типа {@link OrderCreatedEvent}.
     */
    @Bean
    public ProducerFactory<String, OrderCreatedEvent> producerFactory() {
        Map<String, Object> configProps = producerConfigs();
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Создает шаблон Kafka для отправки сообщений типа {@link OrderCreatedEvent}.
     *
     * @return объект {@link KafkaTemplate}, настроенный для отправки сообщений типа {@link OrderCreatedEvent}.
     */
    @Bean
    public KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
