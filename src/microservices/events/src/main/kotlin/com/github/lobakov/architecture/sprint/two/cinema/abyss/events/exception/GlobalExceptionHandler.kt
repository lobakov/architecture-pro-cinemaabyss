package com.github.lobakov.architecture.sprint.two.cinema.abyss.events.exception

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

private const val ISE = "Internal Server Error"

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(
        IllegalArgumentException::class,
        HttpMessageNotReadableException::class,
        MethodArgumentNotValidException::class,
        MethodArgumentTypeMismatchException::class
    )
    fun handleBadRequest(ex: Exception) = ResponseEntity
            .status(BAD_REQUEST)
            .body(mapOf("error" to ISE))

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception) = ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(mapOf("error" to ISE))
}
