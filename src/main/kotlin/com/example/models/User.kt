package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val userId: String,
    val name: String,
    val lastname: String,
    val email: String,
    val username: String,
    val createdAt: Long,
    val password: String? = null,
    val role: String
        )