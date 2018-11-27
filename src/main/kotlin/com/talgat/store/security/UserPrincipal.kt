package com.talgat.store.security

import com.talgat.store.data.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserPrincipal(
        val id: Long,
        val name: String,
        private val username: String,
        val email: String,
        private val password: String,
        private val authorities: List<GrantedAuthority>) : UserDetails {

    override fun getUsername(): String = username
    override fun getPassword(): String = password
    override fun isEnabled(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun getAuthorities(): List<GrantedAuthority> = authorities

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false
        val that = other as UserPrincipal
        return id == that.id
    }

    override fun hashCode(): Int = Objects.hash(id)

    companion object {

        fun create(user: User): UserPrincipal {
            val  authorities = user.roles.map { it ->
                SimpleGrantedAuthority(it.name.name)
            }

            return UserPrincipal(
                    user.id,
                    user.firstName + " " + user.lastName,
                    user.phone,
                    user.email,
                    user.password,
                    authorities
            )
        }
    }
}