package com.example.models

import kotlinx.serialization.Serializable


@Serializable
data class UserResponseParams (
    val success: Boolean,
    val message: String,
    val user: User?
        )