package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model

import java.time.ZonedDateTime

data class Event(
    val id: String,
    val type: String,
    val timestamp: ZonedDateTime,
    val payload: Any
)
