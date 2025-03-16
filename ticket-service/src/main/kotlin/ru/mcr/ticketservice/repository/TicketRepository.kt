package ru.mcr.ticketservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.mcr.ticketservice.entity.Ticket

interface TicketRepository: JpaRepository<Ticket, Long> {
}