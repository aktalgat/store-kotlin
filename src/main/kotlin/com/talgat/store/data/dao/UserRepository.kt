package com.talgat.store.data.dao

import com.talgat.store.data.model.User

interface UserRepository {
    fun findByPhone(phone: String): User?
    fun findById(id: Long) : User?
    fun findByEmail(email: String): User?
    fun save(user: User)
}