package ru.mcr.ticketservice.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.mcr.ticketservice.NotificationType
import ru.mcr.ticketservice.annotation.Monitor
import ru.mcr.ticketservice.dto.NotificationRequest
import ru.mcr.ticketservice.dto.Person
import ru.mcr.ticketservice.exception.IntegrationException
import java.util.*

private const val URL = "http://localhost:8886/api/v1/notification/send"
private const val METRIC_NAME = "ticket_service_requests_total"

@Service
open class NotificationServiceImpl (private val restTemplate: RestTemplate): NotificationService {

    private val log: Logger = LoggerFactory.getLogger(NotificationServiceImpl::class.java)

    private val objectMapper = ObjectMapper()

    @Monitor(metricName = METRIC_NAME)
    override fun sendNotification(orderId: String) {

        val notificationRequest = NotificationRequest().apply {
            message = "Номер Вашего заказа $orderId"
            person = Person().apply {
                id = 1L
                name = "Поликарп Аристархович"
                deviceId = UUID.randomUUID().toString()
            }
            notificationType = NotificationType.EMAIL
        }

        log.info("Запрос в сервис уведомлений: {}", objectMapper.writeValueAsString(notificationRequest))

        val headers = HttpHeaders()
        headers.contentType = org.springframework.http.MediaType.APPLICATION_JSON
        val entity = HttpEntity(notificationRequest, headers)

        val response: ResponseEntity<String> = restTemplate.exchange(URL, HttpMethod.POST, entity, String::class.java)
        checkResponse(response)
    }

    private fun checkResponse(response: ResponseEntity<String>) {
        if (response.statusCode.is2xxSuccessful) {
            log.debug("Сообщение доставлено ${response.body}")
        } else {
            log.error("Ошибка при доставке сообщения. ${response.statusCode}")
            throw IntegrationException()
        }
    }
}