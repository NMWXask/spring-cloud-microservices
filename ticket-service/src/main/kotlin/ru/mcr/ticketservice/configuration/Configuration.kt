package ru.mcr.ticketservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class Configuration {

    @Bean
    open fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}