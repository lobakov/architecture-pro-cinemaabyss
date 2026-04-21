package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.service

import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.Event
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.MovieEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.PaymentEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.UserEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class EventConsumer {

    @KafkaListener(topics = ["user-events"], groupId = "event-group")
    fun consumeUserEvent(event: Event) {
        logger.info("Received User Event: ${event.payload as UserEvent}")
    }

    @KafkaListener(topics = ["payment-events"], groupId = "event-group")
    fun consumePaymentEvent(event: Event) {
        logger.info("Received Payment Event: ${event.payload as PaymentEvent}")
    }

    @KafkaListener(topics = ["movie-events"], groupId = "event-group")
    fun consumeMovieEvent(event: Event) {
        logger.info("Received Movie Event: ${event.payload as MovieEvent}")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(EventConsumer::class.java)
    }
}
