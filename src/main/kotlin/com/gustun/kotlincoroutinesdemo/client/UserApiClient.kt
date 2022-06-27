package com.gustun.kotlincoroutinesdemo.client

import com.gustun.kotlincoroutinesdemo.model.UserDetail
import com.gustun.kotlincoroutinesdemo.model.UserImage
import com.gustun.kotlincoroutinesdemo.model.UserModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToFlow

@Component
class UserApiClient (private val userApiWebClient: WebClient) {
    suspend fun withDetails(id: Int): UserDetail {
        val stopWatch = StopWatch()
        stopWatch.start()
        val userModel =
            userApiWebClient.get().uri("/users/$id")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().awaitBody<UserModel>()

        val userImage =
            userApiWebClient.get().uri("/images/$id/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().awaitBody<UserImage>()

        stopWatch.stop()
        println(stopWatch.totalTimeMillis)
        return UserDetail(userModel, userImage)
    }

    suspend fun withDetailsAsync(id: Int): UserDetail = coroutineScope {
        val stopWatch = StopWatch()
        stopWatch.start()
        val userModel = async {
            userApiWebClient.get().uri("/users/$id")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().awaitBody<UserModel>()
        }

        val userImage = async {
            userApiWebClient.get().uri("/images/$id/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().awaitBody<UserImage>()
        }

        val userDetail = UserDetail(userModel.await(), userImage.await())
        stopWatch.stop()
        println(stopWatch.totalTimeMillis)
        userDetail
    }

    suspend fun findAll(): Flow<UserModel> {
        return userApiWebClient.get()
            .uri("/users")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlow()
    }
}