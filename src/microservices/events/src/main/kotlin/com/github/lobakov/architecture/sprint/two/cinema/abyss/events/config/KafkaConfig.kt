package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.config

import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventType.MOVIE
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventType.PAYMENT
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventType.USER
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {

    @Bean
    fun userTopic(): NewTopic = NewTopic("user-events", 1, 1)

    @Bean
    fun paymentTopic(): NewTopic = NewTopic("payment-events", 1, 1)

    @Bean
    fun movieTopic(): NewTopic = NewTopic("movie-events", 1, 1)

    @Bean
    fun topicMap() = mapOf(
        USER to userTopic().name(),
        PAYMENT to paymentTopic().name(),
        MOVIE to movieTopic().name()
    )
}
