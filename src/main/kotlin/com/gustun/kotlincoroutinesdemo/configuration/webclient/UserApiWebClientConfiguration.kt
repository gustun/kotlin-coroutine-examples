package com.gustun.kotlincoroutinesdemo.configuration.webclient

import com.gustun.kotlincoroutinesdemo.exception.DomainException
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.function.Function


private const val MOCK_API_BASE_URL = "https://62128eddf43692c9c6edfcde.mockapi.io/api/v1"
private const val UNEXPECTED_ERROR_OCCURRED = "Unexpected error occurred!"

@Configuration
class UserApiWebClientConfiguration {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun userApiWebClient(): WebClient {

        return WebClient.builder().filter(errorHandler()).baseUrl(MOCK_API_BASE_URL).build()
    }

    fun errorHandler(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofResponseProcessor { clientResponse: ClientResponse ->
            if (clientResponse.statusCode().is5xxServerError) {
                return@ofResponseProcessor clientResponse
                    .bodyToMono(String::class.java)
                    .flatMap(changeToUserFriendlyMessage())
            }

            if (clientResponse.statusCode().is4xxClientError) {
                return@ofResponseProcessor clientResponse
                    .bodyToMono(String::class.java)
                    .flatMap(changeToUserFriendlyMessage())
            }

            return@ofResponseProcessor Mono.just(clientResponse)
        }
    }

    private fun changeToUserFriendlyMessage() = Function<String, Mono<out ClientResponse>> { errorBody: String? ->
        log.error(errorBody ?: "No error message from client!")
        Mono.error(DomainException(UNEXPECTED_ERROR_OCCURRED))
    }
}