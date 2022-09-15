package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.LoginParams
import com.example.models.User
import io.ktor.server.config.*
import java.util.*

class TokenManager(private val config: HoconApplicationConfig) {

    private val audience = config.property("audience").getString()
    private val secret = config.property("secret").getString()
    private val issuer = config.property("issuer").getString()
    private val expirationDate = System.currentTimeMillis() + 60000
    private val currentTimeInMillis = System.currentTimeMillis()

    fun generateJWTToken(params: LoginParams): String? {
        return JWT.create().withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", params.username)
            .withClaim("password", params.password)
            .withExpiresAt(Date(expirationDate))
            .sign(Algorithm.HMAC256(secret))
    }


    fun refreshJWTToken(token: String): String? {
        return JWT.create().withAudience(audience)
            .withIssuer(issuer)
            .withClaim("oldToken", token)
            .withExpiresAt(Date(expirationDate))
            .sign(Algorithm.HMAC256(secret))
    }

    fun verifyJWTToken() : JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer).build()
    }

}