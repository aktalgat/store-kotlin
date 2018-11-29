package com.talgat.store.api

import com.talgat.store.api.payload.AuthRequest
import com.talgat.store.api.payload.JwtAuthenticationResponse
import com.talgat.store.security.JwtTokenProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AuthController(private val authenticationManager: AuthenticationManager,
                     private val tokenProvider: JwtTokenProvider) : AbstractPublicApi() {

    companion object {
        val log : Logger = LoggerFactory.getLogger(AuthController::class.java)
    }

    @PostMapping("/auth/signin")
    fun authenticateUser(@Valid @RequestBody authRequest: AuthRequest): ResponseEntity<*> {
        log.info("Get request for login: {}", authRequest)

        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authRequest.login,
                        authRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok<Any>(JwtAuthenticationResponse(jwt))
    }
}
