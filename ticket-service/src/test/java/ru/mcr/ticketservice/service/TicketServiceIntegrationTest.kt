package ru.mcr.ticketservice.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TicketServiceIntegrationTest {

    @Autowired
    private lateinit var ticketService: TicketService

    @Test
    fun `test find ticket by id`() {
        val ticketId = 1L

        val ticket = ticketService.findById(ticketId)

        Assertions.assertNotNull(ticket)
    }
}