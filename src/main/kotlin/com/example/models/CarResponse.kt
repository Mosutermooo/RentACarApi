package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CarResponse(
    val success: Boolean,
    val message: String,
    val data: List<Car>?
)
