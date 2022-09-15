package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val token: String?,
    val message: String
)
