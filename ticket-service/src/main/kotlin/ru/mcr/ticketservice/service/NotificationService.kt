package ru.mcr.ticketservice.service

interface NotificationService {
    fun sendNotification(orderId: String)
}