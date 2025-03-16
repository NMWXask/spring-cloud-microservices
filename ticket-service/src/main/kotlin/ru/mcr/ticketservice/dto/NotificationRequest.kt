package ru.mcr.ticketservice.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import ru.mcr.ticketservice.NotificationType

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class NotificationRequest(
    var message: String? = null,
    var person: Person? = null,
    var notificationType: NotificationType? = null,
)