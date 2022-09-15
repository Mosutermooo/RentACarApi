package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class RentCarResponseParams(
    val success: Boolean,
    val message: String,
    val rentId: Long?,
    val rents: List<AllRentsResponseParams>? = null
)
