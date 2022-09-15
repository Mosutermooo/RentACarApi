package com.example.repository

import com.example.models.Car
import com.example.models.CarResponse
import com.example.models.InsertCarRequestParams
import com.example.service.*

class CarsRepositoryImpl : CarsRepository {

    private val carService: CarService = CarServiceImpl()
    override suspend fun insertCar(insertCarParams: InsertCarRequestParams): CarResponse {
        return carService.insertCar(insertCarParams)
    }

    override suspend fun deleteCar(carId: Int?, userId: String?): CarResponse {
        return carService.deleteCar(carId, userId ?: return CarResponse(
            false,
            "Enter a valid userId",null
        ))
    }

    override suspend fun getCarById(carId: Int?): CarResponse {
        return carService.getCarById(carId)
    }

    override suspend fun getCars(): CarResponse {
        return carService.getCars()
    }

    override suspend fun getCarByType(type: String): List<Car> {
        return carService.getCarByType(type)
    }


}