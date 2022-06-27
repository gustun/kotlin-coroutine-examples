package com.gustun.kotlincoroutinesdemo.service

import com.gustun.kotlincoroutinesdemo.client.UserApiClient
import com.gustun.kotlincoroutinesdemo.model.UserDetail
import com.gustun.kotlincoroutinesdemo.model.UserModel
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class UserService(private val userApiClient: UserApiClient) {
    suspend fun withDetails(id: Int): UserDetail {
        return userApiClient.withDetails(id)
    }

    suspend fun withDetailsAsync(id: Int): UserDetail {
        return userApiClient.withDetailsAsync(id)
    }

    suspend fun findAll(): Flow<UserModel> {
        return userApiClient.findAll()
    }
}