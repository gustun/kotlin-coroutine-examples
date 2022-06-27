package com.gustun.kotlincoroutinesdemo.model

data class ErrorResponse(
    val exception: String,
    val timestamp: Long,
    val errors: List<String?>)