package com.example.models

import kotlinx.serialization.Serializable


@Serializable
data class RentsResponseParams(
    var success: Boolean,
    val message: String,
    val rents: List<AllRentsResponseParams>? = null
)