package com.gustun.kotlincoroutinesdemo.configuration.webclient

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AnotherApiWebClientConfiguration {

    @Bean
    fun webClient2(): WebClient {

        return WebClient.builder().baseUrl("Asd").build()
    }
}