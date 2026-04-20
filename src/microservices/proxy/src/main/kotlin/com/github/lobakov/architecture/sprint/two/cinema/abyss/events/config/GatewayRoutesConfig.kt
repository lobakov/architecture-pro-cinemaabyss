package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.config

import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.config.properties.ProxyProperties
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayRoutesConfig(private val props: ProxyProperties) {

    @Bean
    fun cinemaRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        val routes = builder.routes()

        // 1. EXCLUSIONS: Always to Monolith
        routes.route("monolith-fixed") { r ->
            r.path("/api/users/**", "/api/payments/**", "/api/subscriptions/**")
                .uri(props.monolithUrl)
        }

        if (props.gradualMigration.toBooleanStrict()) {
            // 2. MIGRATION: New Microservice (Percent %)
            routes.route("movies-microservice") { r ->
                r.path("/api/movies/**")
                    .and()
                    // "group" ensures weights add up to 100 across routes in that group
                    .weight("movies_group", props.moviesMigrationPercent.toInt())
                    .uri(props.moviesUrl)
            }

            // 3. MIGRATION: Remainder to Monolith (100 - Percent %)
            routes.route("movies-monolith-fallback") { r ->
                r.path("/api/movies/**")
                    .and()
                    .weight("movies_group", 100 - props.moviesMigrationPercent.toInt())
                    .uri(props.monolithUrl)
            }
        }

        // 4. CATCH-ALL: Everything else to Monolith
        return routes.route("default-monolith") { r ->
            r.path("/**")
                .uri(props.monolithUrl)
        }.build()
    }
}
