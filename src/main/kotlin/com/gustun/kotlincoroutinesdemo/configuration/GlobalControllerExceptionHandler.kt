package com.gustun.kotlincoroutinesdemo.configuration

import com.gustun.kotlincoroutinesdemo.exception.DomainException
import com.gustun.kotlincoroutinesdemo.model.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception?): ResponseEntity<ErrorResponse> {
        log.error("Generic exception occurred.", exception)
        val errorResponse = ErrorResponse("GenericException", System.currentTimeMillis(), listOf("Generic exception occurred."))
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(exception: DomainException): ResponseEntity<ErrorResponse> {
        log.error("DomainException Exception Occurred", exception)
        val errorResponse = ErrorResponse("DomainException", System.currentTimeMillis(), listOf(exception.message))
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}