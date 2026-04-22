package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.controller

import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventResult
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.MovieEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.PaymentEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.UserEvent
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.service.EventService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/events")
class EventController(private val eventService: EventService) {

    @PostMapping("/user")
    @ResponseStatus(CREATED)
    fun sendUserEvent(@RequestBody event: UserEvent): EventResult = eventService.sendUserEvent(event)

    @PostMapping("/payment")
    @ResponseStatus(CREATED)
    fun sendPaymentEvent(@RequestBody event: PaymentEvent): EventResult = eventService.sendPaymentEvent(event)

    @PostMapping("/movie")
    @ResponseStatus(CREATED)
    fun sendMovieEvent(@RequestBody event: MovieEvent): EventResult = eventService.sendMovieEvent(event)

    @GetMapping("/health")
    fun health() = ResponseEntity.ok(mapOf("status" to true))
}
