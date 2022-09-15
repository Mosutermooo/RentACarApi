package com.example.service

import com.example.db.Database
import com.example.models.*

interface CarService {

    val dbConnection: org.ktorm.database.Database
        get() = Database.connect()

    suspend fun insertCar(insertCarParams: InsertCarRequestParams) : CarResponse
    suspend fun deleteCar(carId: Int?, userId: String) : CarResponse
    suspend fun getCarById(carId: Int?) : CarResponse
    suspend fun getCars() : CarResponse
    suspend fun getCarImages(carId: Int): List<CarImage>
    suspend fun getCarByType(type: String): List<Car>

}