package com.talgat.store.security

import com.talgat.store.data.dao.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    companion object {
        val log : Logger = LoggerFactory.getLogger(CustomUserDetailsService::class.java)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(phone: String): UserDetails {
        log.info("Find user by phone: {}", phone)
        val user = userRepository.findByPhone(phone) ?:
                throw UsernameNotFoundException("User not found with phone : $phone")
        log.info("User found: {}", user)
        return UserPrincipal.create(user)
    }

    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id) ?: throw UsernameNotFoundException("User id $id")

        return UserPrincipal.create(user)
    }
}
