package com.example.db.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar


object RentTable: Table<Nothing>("rents"){
    val carId = int("carId")
    val rentId = long("rentId")
    val userId = varchar("userId")
    val price = int("price")
    val rentTime = long("rentTime")
}