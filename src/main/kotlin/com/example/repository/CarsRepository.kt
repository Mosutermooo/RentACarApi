package com.example.repository

import com.example.models.Car
import com.example.models.CarResponse
import com.example.models.InsertCarRequestParams
import com.example.models.RentsResponseParams

interface CarsRepository {
    suspend fun insertCar(insertCarParams: InsertCarRequestParams) : CarResponse
    suspend fun deleteCar(carId: Int?, userId: String?) : CarResponse
    suspend fun getCarById(carId: Int?) : CarResponse
    suspend fun getCars() : CarResponse
    suspend fun getCarByType(type: String): List<Car>
    suspend fun getUserRents(userId: String?): RentsResponseParams
    suspend fun getAllRents(): RentsResponseParams
    suspend fun getRentsByRentId(rentId: Long?): RentsResponseParams
}