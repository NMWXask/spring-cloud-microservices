package ru.mcr.ticketservice.service

import ru.mcr.ticketservice.dto.TicketResponseDto

interface TicketService {

    fun findById(ticketId: Long): TicketResponseDto?

    fun fallBackGetArtistById(ticketId: Long, ex: Throwable): TicketResponseDto?
}