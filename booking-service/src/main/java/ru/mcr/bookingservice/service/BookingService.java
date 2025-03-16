package ru.mcr.bookingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.mcr.bookingservice.annotation.Monitor;
import ru.mcr.bookingservice.dto.CreateOrderDto;
import ru.mcr.bookingservice.exception.ErrorMessage;
import ru.mcr.core.event.order.OrderCreatedEvent;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    private static final  String METRIC_NAME = "booking_service_requests_total";

    @Monitor(metricName = METRIC_NAME)
    public String createOrder(CreateOrderDto createOrderDto) {

        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(UUID.randomUUID().toString())
                .artistId(createOrderDto.getArtistId())
                .userId(createOrderDto.getUserId())
                .ticketId(createOrderDto.getTicketId())
                .seat(createOrderDto.getSeat())
                .build();

        String key = orderCreatedEvent.getOrderId();

        SendResult<String, OrderCreatedEvent> result;
            try {
                result = kafkaTemplate.send("booking-create-event-topic", key, orderCreatedEvent).get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("Ошибка во время отправки сообщения : {}", e.getMessage());
                throw new ErrorMessage(e.getMessage());
            }

            LOGGER.info("Partition: {}", result.getRecordMetadata().partition());
            LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
            LOGGER.info("Offset: {}", result.getRecordMetadata().offset());
            LOGGER.info("Return|: {}", key);

        return key;
    }
}
