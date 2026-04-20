package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.service

import com.fasterxml.uuid.impl.TimeBasedGenerator
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.Event
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventDescription
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventResultType.SUCCESS
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventType
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventType.MOVIE
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventType.PAYMENT
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventType.USER
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.MovieEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.PaymentEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventResult
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.UserEvent
import org.springframework.stereotype.Service
import java.time.ZonedDateTime.now

@Service
class EventService(
    private val generator: TimeBasedGenerator,
    private val eventProducer: EventProducer
) {

    fun sendUserEvent(
        userEvent: UserEvent
    ): EventResult = wrapResult(wrapAndSend(USER, userEvent))

    fun sendPaymentEvent(
        paymentEvent: PaymentEvent
    ): EventResult = wrapResult(wrapAndSend(PAYMENT, paymentEvent))

    fun sendMovieEvent(
        movieEvent: MovieEvent
    ): EventResult = wrapResult(wrapAndSend(MOVIE, movieEvent))

    private fun wrapAndSend(eventType: EventType, event: Any): Event {
        val wrappedEvent = wrapEvent(eventType.typeName, event)
        eventProducer.send(eventType, wrappedEvent)
        return wrappedEvent
    }

    private fun wrapEvent(type: String, eventData: Any): Event = Event(
        id = generator.generate().toString(),
        type = type,
        timestamp = now(),
        payload = eventData
    )

    private fun wrapResult(event: Event) = EventResult(
        status = SUCCESS.caption,
        partition = 0,
        offset = 42,
        event = EventDescription(event.toString())
    )
}
