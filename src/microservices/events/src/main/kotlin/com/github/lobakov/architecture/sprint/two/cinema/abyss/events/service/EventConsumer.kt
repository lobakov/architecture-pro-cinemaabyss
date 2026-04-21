package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.Event
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.MovieEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.PaymentEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.UserEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import kotlin.jvm.java

@Service
class EventConsumer(
    private val objectMapper: ObjectMapper
) {

    @KafkaListener(topics = ["user-events"], groupId = "event-group")
    fun consumeUserEvent(event: Event) {
        val userEvent = objectMapper.convertValue(event.payload, UserEvent::class.java)
        logger.info("Received User Event: $userEvent")
    }

    @KafkaListener(topics = ["payment-events"], groupId = "event-group")
    fun consumePaymentEvent(event: Event) {
        val paymentEvent = objectMapper.convertValue(event.payload, PaymentEvent::class.java)
        logger.info("Received Payment Event: $paymentEvent")
    }

    @KafkaListener(topics = ["movie-events"], groupId = "event-group")
    fun consumeMovieEvent(event: Event) {
        val movieEvent = objectMapper.convertValue(event.payload, MovieEvent::class.java)
        logger.info("Received Movie Event: $movieEvent")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(EventConsumer::class.java)
    }
}
