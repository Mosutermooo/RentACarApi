package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class LocationModelResponseParams(
    val success: Boolean,
    val message: String,
    val Locations: List<LocationsModel>? = null,
    val location: LocationsModel? = null
)
