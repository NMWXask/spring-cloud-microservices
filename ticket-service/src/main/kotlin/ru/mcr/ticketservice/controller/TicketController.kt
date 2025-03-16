package ru.mcr.ticketservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mcr.ticketservice.dto.TicketResponseDto
import ru.mcr.ticketservice.service.TicketService

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
class TicketController (private val ticketService: TicketService) {

    private val objectMapper = ObjectMapper()

    private val logger: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    @GetMapping("/{ticketId}")
    fun getTicket(@PathVariable("ticketId") ticketId: Long): ResponseEntity<TicketResponseDto?> {
        val ticketResponse = ticketService.findById(ticketId)
        return if (ticketResponse != null) {
            logger.info("Ответ контроллера билетов : {}", objectMapper.writeValueAsString(ticketResponse))
            ResponseEntity(ticketResponse, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}