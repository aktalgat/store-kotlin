package com.talgat.store.security

import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

@Component
class JwtTokenProvider {

    companion object {
        val log : Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
    }

    @Value("\${app.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${app.jwtExpirationInMs}")
    private val jwtExpirationInMs: Int = 0

    fun generateToken(authentication: Authentication): String {

        val userPrincipal = authentication.principal as UserPrincipal

        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs)

        val signatureAlgorithm = SignatureAlgorithm.HS512
        val jwtSecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret)
        val signingKey = SecretKeySpec(jwtSecretBytes, signatureAlgorithm.jcaName)

        return Jwts.builder()
                .setSubject(java.lang.Long.toString(userPrincipal.id))
                .claim("userName", userPrincipal.name)
                .claim("userEmail", userPrincipal.email)
                .claim("roles", userPrincipal.authorities)
                .claim("login", userPrincipal.username)
                .setIssuedAt(Date())
                .setExpiration(expiryDate)
                .signWith(signingKey)
                .compact()
    }

    fun getUserIdFromJWT(token: String): Long {
        val claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                .parseClaimsJws(token)
                .body

        return java.lang.Long.parseLong(claims.subject)
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret)).parseClaimsJws(authToken)
            return true
        } catch (ex: SecurityException) {
            log.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            log.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            log.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            log.error("JWT claims string is empty.")
        }

        return false
    }
}