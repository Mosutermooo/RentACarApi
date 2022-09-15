package com.example.models

import kotlinx.serialization.Serializable


@Serializable
data class CarImageRequestParams(
    val imageUrl: String,
)
