package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginParams(
    val username: String,
    val password: String
)
