package com.example.models

import kotlinx.serialization.Serializable


@Serializable
data class LocationModelRequestParams(
    val lat: String,
    val lng: String,
    val location_name: String,
    val city: String,
    val userId: String
)
