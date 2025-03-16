package ru.mcr.ticketservice.configuration

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.util.backoff.FixedBackOff
import ru.mcr.ticketservice.exception.NonRetryableException
import ru.mcr.ticketservice.exception.RetryableException

@EnableKafka
@Configuration
open class KafkaConsumerConfig {

    @Autowired
    private lateinit var environment: Environment

    @Bean
    open fun consumerFactory(): ConsumerFactory<String, Any> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = environment.getProperty("spring.kafka.consumer.bootstrap-servers")!!
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        config[ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS] = StringDeserializer::class.java
        config[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = JsonDeserializer::class.java
        config[JsonDeserializer.TRUSTED_PACKAGES] = "ru.mcr.core.event.order"
        config[ConsumerConfig.GROUP_ID_CONFIG] = environment.getProperty("spring.kafka.consumer.group-id")!!
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    open fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, Any>, kafkaTemplate: KafkaTemplate<*, *>): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val errorHandler = DefaultErrorHandler(DeadLetterPublishingRecoverer(kafkaTemplate), FixedBackOff(3000L, 3))
        errorHandler.addNotRetryableExceptions(NonRetryableException::class.java)
        errorHandler.addRetryableExceptions(RetryableException::class.java)
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.consumerFactory = consumerFactory
        factory.setCommonErrorHandler(errorHandler)
        return factory
    }

    @Bean
    open fun kafkaTemplate(producerFactory: ProducerFactory<String, Any>): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory)
    }

    @Bean
    open fun producerFactory(): ProducerFactory<String, Any> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = environment.getProperty("spring.kafka.consumer.bootstrap-servers")!!
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory(config)
    }
}