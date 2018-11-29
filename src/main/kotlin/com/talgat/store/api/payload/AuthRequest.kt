package com.talgat.store.api.payload

data class AuthRequest(
        val login: String,
        val password: String
)
