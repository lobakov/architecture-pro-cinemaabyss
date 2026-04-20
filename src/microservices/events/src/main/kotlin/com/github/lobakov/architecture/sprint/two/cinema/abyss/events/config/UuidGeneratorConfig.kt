package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.config

import com.fasterxml.uuid.EthernetAddress
import com.fasterxml.uuid.UUIDTimer
import com.fasterxml.uuid.ext.FileBasedTimestampSynchronizer
import com.fasterxml.uuid.impl.TimeBasedGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Random

@Configuration
class UuidGeneratorConfig {

    @Bean
    fun getGenerator() = TimeBasedGenerator(
        EthernetAddress(3223432442L),
        UUIDTimer(Random(), FileBasedTimestampSynchronizer())
    )
}
