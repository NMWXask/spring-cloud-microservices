package ru.mcr.ticketservice.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.mcr.ticketservice.annotation.Monitor
import ru.mcr.ticketservice.client.ArtistClient
import ru.mcr.ticketservice.dto.ArtistDto
import ru.mcr.ticketservice.dto.TicketResponseDto
import ru.mcr.ticketservice.entity.Ticket
import ru.mcr.ticketservice.exception.TicketNotFoundException
import ru.mcr.ticketservice.repository.TicketRepository

private const val METRIC_NAME = "ticket_service_requests_total"

@Service
open class TicketServiceImpl(
    private val ticketRepository: TicketRepository,
    private val artistClient: ArtistClient
) : TicketService {

    private val objectMapper = ObjectMapper()
    private val logger: Logger by lazy { LoggerFactory.getLogger(this::class.java) }


    override fun findById(ticketId: Long): TicketResponseDto? {
        val ticket = ticketRepository.findById(ticketId).orElseThrow {
            TicketNotFoundException("Ticket with ID $ticketId not found")
        }
        logger.info("Ответ сервиса билетов : {}", objectMapper.writeValueAsString(ticket))
        return getArtistInfo(ticket)
    }

    @Monitor(metricName = METRIC_NAME)
    @CircuitBreaker(name = "artistService", fallbackMethod = "fallBackGetArtistById")
    @Retry(name = "artistService", fallbackMethod = "fallBackGetArtistById")
    fun getArtistInfo(ticket: Ticket): TicketResponseDto {
        val artistId = ticket.artistId
        val artist = artistClient.getArtistById(artistId)
        logger.info("Ответ сервиса артистов : {}", objectMapper.writeValueAsString(artist))
        return TicketResponseDto(
            ticketId = ticket.ticketId,
            artistDto = artist,
            seat = ticket.seat,
            userId = ticket.userId
        )
    }

    override fun fallBackGetArtistById(ticketId: Long, ex: Throwable): TicketResponseDto {
        println("Fallback method called due to: ${ex.message}")

        return TicketResponseDto(
            ticketId = ticketId,
            userId = -1,
            artistDto = ArtistDto(
                id = -1,
                name = "Unknown Artist",
                genre = "Unknown Genre",
                performanceTime = "default"
            ),
            seat = "Unknown Seat"
        )
    }
}