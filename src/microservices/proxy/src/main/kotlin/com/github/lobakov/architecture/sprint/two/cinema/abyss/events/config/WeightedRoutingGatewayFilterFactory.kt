package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.config

import com.github.lobakov.architecture.sprint.two.cinema.abyss.events.config.properties.ProxyProperties
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import kotlin.random.Random

@Component
class WeightedRoutingGatewayFilterFactory(
    proxyProperties: ProxyProperties
) : AbstractGatewayFilterFactory<WeightedRoutingGatewayFilterFactory.Config>(Config::class.java) {

    private val migrationDisabled = proxyProperties.gradualMigration.toBooleanStrict().not()
    private val trafficShare = proxyProperties.moviesMigrationPercent.toInt()
    private val monolith = URI.create(proxyProperties.monolithUrl)
    private val microservice = URI.create(proxyProperties.moviesUrl)
    private val excludeRouting = listOf("/api/users", "/api/payments", "/api/subscriptions")

    private val logger = LoggerFactory.getLogger("MY")

    override fun apply(config: Config): GatewayFilter = GatewayFilter { exchange, chain ->
        val originalRequest = exchange.request
        val requestPath = originalRequest.path.toString()
        val originalUri = originalRequest.uri
        val targetUri = composeTargetUri(originalUri, requestPath)
        logger.info("Routing: ${originalUri} -> ${targetUri}")

        val mutatedExchange = mutateExchange(exchange, originalRequest, targetUri)
        chain.filter(mutatedExchange)
    }

    private fun composeTargetUri(originalUri: URI, path: String): URI {
        val targetUri = determineTargetUri(excludeRouting.any { path.startsWith(it) })
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
        val mutatedRequest = originalRequest
            .mutate()
            .uri(targetUri)
            .build()

        val mutatedExchange = exchange.mutate()
            .request(mutatedRequest)
            .build()
        mutatedExchange.attributes[GATEWAY_REQUEST_URL_ATTR] = targetUri
        mutatedExchange.attributes.remove(GATEWAY_SCHEME_PREFIX_ATTR)

        return mutatedExchange
    }

    private fun determineTargetUri(shouldExclude: Boolean): URI = if (shouldExclude || migrationDisabled || !newUrl()) monolith else microservice

    private fun newUrl(): Boolean = Random.nextInt(100) < trafficShare

    class Config
}