package com.example.models

import com.example.db.entities.LocationsTable.primaryKey
import kotlinx.serialization.Serializable
import org.ktorm.schema.int
import org.ktorm.schema.varchar
@Serializable
data class LocationsModel(
    val id : Int,
    val lat : String,
    val lng :String,
    val location_name : String,
    val city : String
)
