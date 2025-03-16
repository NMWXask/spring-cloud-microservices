package ru.mcr.ticketservice.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Person(
    var id: Long? = null,
    var name: String? = null,
    var deviceId: String? = null
    )