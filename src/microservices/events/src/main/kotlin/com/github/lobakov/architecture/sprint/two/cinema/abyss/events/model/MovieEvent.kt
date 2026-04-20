package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MovieEvent(
    val movieId: Int,
    val title: String,
    val action: String,
    val rating: Float? = null,
    val genres: List<String> = emptyList(),
    val userId: Int? = null,
    val description: String? = null
)
