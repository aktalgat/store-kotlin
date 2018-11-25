package com.talgat.store.exception

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Slf4j
@ControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException,
                                              headers: HttpHeaders, status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {
        val error = "Malformed JSON request"
        return buildResponseEntity(ApiExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST, error))
    }

    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        val error = "Invalid JSON body"
        return buildResponseEntity(ApiExceptionResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, error))
    }

    @ExceptionHandler(InternalException::class)
    fun errorRunProcessHandle(ex: InternalException): ResponseEntity<Any> {
        return buildResponseEntity(ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: ""))
    }

    private fun buildResponseEntity(apiExceptionResponse: ApiExceptionResponse): ResponseEntity<Any> {
        return ResponseEntity(apiExceptionResponse, apiExceptionResponse.status)
    }
}
