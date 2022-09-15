package com.example.models

import kotlinx.serialization.Serializable


@Serializable
data class Rent (
    val carId: Int,
    val userId: String,
    val price: Int,
    val rentTime: Long,
    val rentId: Long
        )