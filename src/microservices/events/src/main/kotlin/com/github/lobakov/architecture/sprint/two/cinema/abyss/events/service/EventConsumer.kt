package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.service

import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.MovieEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.PaymentEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.UserEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class EventConsumer {

    @KafkaListener(topics = ["user-events"], groupId = "event-group")
    fun consumeUserEvent(event: UserEvent) {
        logger.info("Received User Event: $event")
    }

    @KafkaListener(topics = ["payment-events"], groupId = "event-group")
    fun consumePaymentEvent(event: PaymentEvent) {
        logger.info("Received Payment Event: $event")
    }

    @KafkaListener(topics = ["movie-events"], groupId = "event-group")
    fun consumeMovieEvent(event: MovieEvent) {
        logger.info("Received Movie Event: $event")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(EventConsumer::class.java)
    }
}
