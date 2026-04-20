package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.ZonedDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentEvent(
    val paymentId: Int,
    val userId: Int,
    val amount: Float,
    val status: String,
    val timestamp: ZonedDateTime,
    val methodType: String? = null
)
