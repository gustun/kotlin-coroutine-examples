package com.gustun.kotlincoroutinesdemo.model

import java.time.LocalDate

data class UserModel(
    val id: Int,
    val name: String,
    val createdAt: LocalDate
)

data class UserImage(
    val id: Int,
    val avatar: String,
)

data class UserDetail(
    val id: Int,
    val name: String,
    val createdAt: LocalDate,
    val profileImageUrl: String
) {
    constructor(userModel: UserModel, userImage: UserImage): this(userModel.id, userModel.name, userModel.createdAt, userImage.avatar)
}