package com.talgat.store.model

data class User(
        val id: Long,
        val name: String,
        val username: String,
        val email: String,
        val password: String,
        val roles: Set<Role>
)