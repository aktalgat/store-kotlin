package com.talgat.store.data.model

data class User(
        val id: Long,
        val phone: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val enabled: Boolean,
        val roles: List<Role>
)