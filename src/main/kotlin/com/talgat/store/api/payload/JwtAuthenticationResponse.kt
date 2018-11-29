package com.talgat.store.api.payload

data class JwtAuthenticationResponse(
        val accessToken : String,
        val tokenType : String = "Bearer"
)
