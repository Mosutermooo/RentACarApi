package com.example.service

import com.example.db.Database
import com.example.models.Rent
import com.example.models.RentCarRequestParams
import com.example.models.RentCarResponseParams
import com.example.models.RentsResponseParams

interface RentCarService {

    val dbConnection: org.ktorm.database.Database
        get() = Database.connect()


    suspend fun rentACar(params: RentCarRequestParams): RentCarResponseParams
    suspend fun generateRentId() : Long
    suspend fun changeCarStatus(carId: Int, status: String): RentCarResponseParams
    suspend fun changeCarPrice(carId: Int, price: Int, userId: String): RentCarResponseParams
    fun updateCarStatus(carId: Int?, updateIn: Long)
    suspend fun getCarStatus(carId: Int?) : String

}