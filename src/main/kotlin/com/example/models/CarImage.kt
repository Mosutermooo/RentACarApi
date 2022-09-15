package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CarImage(
    val id: Int,
    val imageUrl: String,
    val carId: Int
)
