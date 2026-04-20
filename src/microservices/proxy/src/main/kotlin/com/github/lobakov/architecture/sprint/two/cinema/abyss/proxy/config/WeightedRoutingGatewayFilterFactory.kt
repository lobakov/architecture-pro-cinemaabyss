package com.github.lobakov.architecture.sprint.two.cinema.abyss.proxy.config

import com.github.lobakov.architecture.sprint.two.cinema.abyss.proxy.config.properties.ProxyProperties
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import kotlin.jvm.java
import kotlin.random.Random

@Component
class WeightedRoutingGatewayFilterFactory(
    proxyProperties: ProxyProperties
) : AbstractGatewayFilterFactory<WeightedRoutingGatewayFilterFactory.Config>(Config::class.java) {

    private val migrationEnabled = proxyProperties.gradualMigration.toBooleanStrict()
    private val trafficShare = proxyProperties.moviesMigrationPercent.toInt()
    private val monolith = URI.create(proxyProperties.monolithUrl)
    private val microservice = URI.create(proxyProperties.moviesUrl)

    override fun apply(config: Config): GatewayFilter = GatewayFilter { exchange, chain ->
        val originalRequest = exchange.request
        val originalUri = originalRequest.uri
        val targetUri = composeTargetUri(originalUri)
        val mutatedExchange = mutateExchange(exchange, originalRequest, targetUri)
        chain.filter(mutatedExchange)
    }

    private fun composeTargetUri(originalUri: URI): URI {
        val targetUri = determineTargetUri()
        val finalUri = UriComponentsBuilder.fromUri(targetUri)
            .path(originalUri.rawPath)
            .replaceQuery(originalUri.rawQuery)
            .build()
            .toUri()
        return finalUri
    }

    private fun mutateExchange(
        exchange: ServerWebExchange,
        originalRequest: ServerHttpRequest,
        targetUri: URI
    ): ServerWebExchange {
        val mutatedExchange = exchange.mutate()
            .request(originalRequest
                    .mutate()
                    .uri(targetUri)
                    .build())
            .build()
        mutatedExchange.attributes[GATEWAY_REQUEST_URL_ATTR] = targetUri
        return mutatedExchange
    }

    private fun determineTargetUri(): URI = if (migrationEnabled && newUrl()) microservice else monolith

    private fun newUrl(): Boolean = Random.nextInt(100) < trafficShare

    class Config
}