package com.gustun.kotlincoroutinesdemo.configuration.webclient

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

private const val MOCK_API_BASE_URL = "https://62128eddf43692c9c6edfcde.mockapi.io/api/v1"

@Configuration
class UserApiWebClientConfiguration {

    @Bean
    fun userApiWebClient(): WebClient {

        return WebClient.builder().baseUrl(MOCK_API_BASE_URL).build()
    }
}