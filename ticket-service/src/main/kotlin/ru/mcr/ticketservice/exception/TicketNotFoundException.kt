package ru.mcr.ticketservice.exception

import java.lang.Exception

class TicketNotFoundException(override val message: String) : Exception(message) {
}