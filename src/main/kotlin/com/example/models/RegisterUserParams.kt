package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserParams (
    val email: String,
    val username: String,
    val password: String,
        )