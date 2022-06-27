package com.gustun.kotlincoroutinesdemo.controller

import com.gustun.kotlincoroutinesdemo.model.UserDetail
import com.gustun.kotlincoroutinesdemo.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    // see: https://stackoverflow.com/questions/55684117/how-to-return-a-kotlin-coroutines-flow-in-spring-reactive-webclient
    @GetMapping("")
    suspend fun findAll() = userService.findAll()

    @GetMapping("/{id}")
    suspend fun findOne(@PathVariable id: Int): UserDetail = userService.withDetails(id)
}