package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val userId: String?,
    val role: String? = null
        )