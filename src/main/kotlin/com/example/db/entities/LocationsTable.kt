package com.example.db.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object LocationsTable : Table<Nothing>("locations")  {

    val id = int("id").primaryKey()
    val lat = varchar("lat")
    val lng = varchar("lng")
    val location_name = varchar("location_name")
    val city = varchar("city")

}