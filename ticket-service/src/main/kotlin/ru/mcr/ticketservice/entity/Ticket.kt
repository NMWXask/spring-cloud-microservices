package ru.mcr.ticketservice.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tickets")
class Ticket (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    val ticketId: Long? = null,
    @Column(name = "user_id")
    val userId: Long? = null,
    @Column(name="artist_id")
    val artistId: Long? = null,
    @Column(name = "seat")
    val seat: String? = null
)