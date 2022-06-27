package com.gustun.kotlincoroutinesdemo.controller

import com.gustun.kotlincoroutinesdemo.model.UserDetail
import com.gustun.kotlincoroutinesdemo.service.WebClientService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users-async")
class UserControllerAsync(private val webClientService: WebClientService) {
    @GetMapping("/{id}")
    suspend fun findOne(@PathVariable id: Int): UserDetail = webClientService.withDetailsAsync(id)

}