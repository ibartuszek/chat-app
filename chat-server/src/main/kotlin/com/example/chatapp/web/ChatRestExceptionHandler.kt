package com.example.chatapp.web

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ChatRestExceptionHandler {

    companion object {
        private const val DEFAULT_MESSAGE = "Some error happened during the request!"
        private val log = LoggerFactory.getLogger(ChatRestExceptionHandler::class.java)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): String {
        log.error("Error happened during binding validation bindingResult=${exception.bindingResult}")
        return exception.bindingResult.allErrors
                .map { it.defaultMessage }
                .first() ?: DEFAULT_MESSAGE
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoSuchElementException(exception: NoSuchElementException): String {
        log.error("Error happened exception=$exception")
        return exception.message ?: DEFAULT_MESSAGE
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleUnspecifiedException(exception: Exception): Map<String, String> {
        log.error("Unexpected error happened: exception=$exception")
        return mapOf("error" to (exception.message ?: DEFAULT_MESSAGE))
    }

}
