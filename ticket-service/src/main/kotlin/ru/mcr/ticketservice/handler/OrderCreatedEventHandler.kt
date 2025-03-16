package ru.mcr.ticketservice.handler

import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.mcr.core.event.order.OrderCreatedEvent
import ru.mcr.ticketservice.service.NotificationService
import ru.mcr.ticketservice.service.TicketService


@Component
@KafkaListener(topicPattern = "booking-create-event-topic", groupId = "order-created-event")
@RequiredArgsConstructor
class OrderCreatedEventHandler(
    private val notificationService: NotificationService,
    private val ticketService: TicketService) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    @KafkaHandler
    fun handle(orderCreatedEvent: OrderCreatedEvent) {
        logger.info("Received event: {}", orderCreatedEvent)
        val orderId = orderCreatedEvent.orderId
        val ticketId = orderCreatedEvent.ticketId

        if (orderCreatedEvent.orderId != "") {
            notificationService.sendNotification(orderId)
        }

        if (orderCreatedEvent.ticketId != null) {
            ticketService.findById(ticketId)
        }
    }

}