package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    fun health(): Mono<Map<String, Boolean>> {
        return Mono.just(mapOf("status" to true))
    }
}
