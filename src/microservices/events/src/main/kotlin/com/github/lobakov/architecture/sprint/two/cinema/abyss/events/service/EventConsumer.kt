package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.service

import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.Event
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class EventConsumer {

    @KafkaListener(topics = ["user-events"], groupId = "event-group")
    fun consumeUserEvent(event: Event) {
        logger.info("=== Received raw event on user-events topic ===")
        log(event)
    }

    @KafkaListener(topics = ["payment-events"], groupId = "event-group")
    fun consumePaymentEvent(event: Event) {
        logger.info("=== Received raw event on payment-events topic ===")
        log(event)
    }

    @KafkaListener(topics = ["movie-events"], groupId = "event-group")
    fun consumeMovieEvent(event: Event) {
        logger.info("=== Received raw event on movie-events topic ===")
        log(event)
    }

    private fun log(event: Event) {
        logger.info("Event ID: ${event.id}")
        logger.info("Event Type: ${event.type}")
        logger.info("Event Timestamp: ${event.timestamp}")
        logger.info("Payload class: ${event.payload::class.java.name}")
        logger.info("Payload content: ${event.payload}")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(EventConsumer::class.java)
    }
}
