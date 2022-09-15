package com.example.service

import com.example.db.Database
import com.example.models.*

interface AdminService {

    val dbConnection: org.ktorm.database.Database
        get() = Database.connect()

    suspend fun allRents(userId: String?) : AllRentsResponseParams
    suspend fun getCarImages(carId: Int): List<CarImage>

}