package ru.mcr.ticketservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(scanBasePackages = ["ru.mcr.ticketservice"])
@EnableFeignClients(basePackages = ["ru.mcr.ticketservice"])
@EnableDiscoveryClient
open class TicketServiceApplication

fun main(args: Array<String>) {
	runApplication<TicketServiceApplication>(*args)
}
