package ru.mcr.ticketservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.mcr.ticketservice.dto.ArtistDto

@FeignClient(name = "ARTIST-SERVICE", url = "http://localhost:8083")
interface ArtistClient {

    @GetMapping("/artist/{id}")
    fun getArtistById(@PathVariable("id") id: Long?): ArtistDto
}