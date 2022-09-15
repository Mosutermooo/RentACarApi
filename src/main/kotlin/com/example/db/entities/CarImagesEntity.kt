package com.example.db.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object CarImagesEntity : Table<Nothing>("carsimages") {
    val id = int("id").primaryKey()
    val image_url = varchar("image_url")
    val car_id = int("car_id")
}