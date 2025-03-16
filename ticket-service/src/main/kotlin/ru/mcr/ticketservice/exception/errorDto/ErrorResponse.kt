package ru.mcr.ticketservice.exception.errorDto

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: String
)
