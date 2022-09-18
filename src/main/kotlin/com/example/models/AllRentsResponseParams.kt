package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class AllRentsResponseParams(
    val success: Boolean? = null,
    val message: String? = null,
    val price: Int?,
    val rentTime: Long?,
    val rentId: Long?,
    val car: Car?,
    val user: User? = null
        )