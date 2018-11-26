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
    override fun getAuthorities(): List<out GrantedAuthority> = authorities

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserPrincipal?
        return id == that!!.id
    }

    override fun hashCode(): Int = Objects.hash(id)

    companion object {

        fun create(user: User): UserPrincipal {
            val  authorities = user.roles.map { it ->
                SimpleGrantedAuthority(it.name.name)
            }

            return UserPrincipal(
                    user.id,
                    user.name,
                    user.username,
                    user.email,
                    user.password,
                    authorities
            )
        }
    }
}