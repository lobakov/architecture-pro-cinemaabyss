package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Configuration

@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "proxy")
class ProxyProperties {
    lateinit var monolithUrl: String
    lateinit var moviesUrl: String
    lateinit var eventsUrl: String
    lateinit var gradualMigration: String
    lateinit var moviesMigrationPercent: String
}
