package com.gustun.kotlincoroutinesdemo.service

import com.gustun.kotlincoroutinesdemo.model.UserDetail
import com.gustun.kotlincoroutinesdemo.model.UserImage
import com.gustun.kotlincoroutinesdemo.model.UserModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.StopWatch
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.client.bodyToFlow

@Service
class WebClientService(private val client: WebClient) {
    suspend fun withDetails(id: Int): UserDetail {
        val stopWatch = StopWatch()
        stopWatch.start()
        val userModel =
            client.get().uri("/users/$id")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange().awaitBody<UserModel>()

        val userImage =
            client.get().uri("/images/$id/")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange().awaitBody<UserImage>()

        stopWatch.stop()
        println(stopWatch.totalTimeMillis)
        return UserDetail(userModel, userImage)
    }

    suspend fun withDetailsAsync(id: Int): UserDetail = coroutineScope {
        val stopWatch = StopWatch()
        stopWatch.start()
        val userModel = async {
            client.get().uri("/users/$id")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange().awaitBody<UserModel>()
        }

        val userImage = async {
            client.get().uri("/images/$id/")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange().awaitBody<UserImage>()
        }

        val userDetail = UserDetail(userModel.await(), userImage.await())
        stopWatch.stop()
        println(stopWatch.totalTimeMillis)
        userDetail
    }

    suspend fun findAll(): Flow<UserModel> {
        return client.get()
            .uri("/users")
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange()
            .bodyToFlow()
    }
}