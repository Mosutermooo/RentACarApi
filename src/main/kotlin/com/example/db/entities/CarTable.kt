package com.example.db.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object CarTable : Table<Nothing>("cars") {

    val id = int("id").primaryKey()
    val car_Brand = varchar("car_Brand")
    val car_Model  = varchar("car_Model")
    val car_Type  = varchar("car_Type")
    val total_price = int("total_price")
    val status = varchar("status")
    val lat = varchar("lat")
    val lng = varchar("lng")

}