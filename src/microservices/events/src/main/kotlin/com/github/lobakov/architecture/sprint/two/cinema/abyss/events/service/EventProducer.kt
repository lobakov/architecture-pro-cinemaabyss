package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.service

import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.Event
import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.model.EventType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class EventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Event>,
    private val topicMap: Map<EventType, String>
) {

    fun send(topicType: EventType, event: Event) = kafkaTemplate.send(topicMap[topicType]!!,  event.id, event)
}
