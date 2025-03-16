package ru.mcr.ticketservice.dto

data class TicketResponseDto(
    val ticketId: Long? = null,
    val userId: Long? = null,
    val artistDto: ArtistDto? = null,
    val seat: String? = null
)