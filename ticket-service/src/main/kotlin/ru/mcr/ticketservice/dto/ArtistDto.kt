package ru.mcr.ticketservice.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ArtistDto @JsonCreator constructor(
    @JsonProperty("id") val id: Long? = null,
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("genre") val genre: String? = null,
    @JsonProperty("performanceTime") val performanceTime: String? = null
)