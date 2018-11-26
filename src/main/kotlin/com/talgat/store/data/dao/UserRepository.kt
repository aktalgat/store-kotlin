package com.talgat.store.data.dao

import com.talgat.store.data.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    fun findByEmail(email: String): User?

    fun findByUsernameOrEmail(username: String, email: String): User?

    fun findByIdIn(userIds: List<Long>): List<User>

    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun findById(id: Long) : User?
}