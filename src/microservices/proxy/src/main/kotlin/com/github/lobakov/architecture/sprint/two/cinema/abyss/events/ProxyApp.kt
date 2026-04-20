package com.github.lobakov.architecture.sprint.two.cinema.abyss.events

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProxyApp

fun main(args: Array<String>) {
    runApplication<ProxyApp>(*args)
}
