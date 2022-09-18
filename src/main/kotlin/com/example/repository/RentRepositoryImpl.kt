package com.example.repository

import com.example.models.RentCarRequestParams
import com.example.models.RentCarResponseParams
import com.example.service.CarService
import com.example.service.CarServiceImpl
import com.example.service.RentCarService
import com.example.service.RentCarServiceImpl

class RentRepositoryImpl : RentCarRepository {

    private val rentService: RentCarService = RentCarServiceImpl()

    override suspend fun rentACar(params: RentCarRequestParams): RentCarResponseParams {
        return rentService.rentACar(params)
    }

    override suspend fun changeCarStatus(carId: Int?, status: String?): RentCarResponseParams {
        return if(carId != null && status != null){
            rentService.changeCarStatus(carId, status)
        }else{
            return RentCarResponseParams(
                false,
                "Please insert valid query params", null
            )
        }
    }

    override suspend fun changeCarPrice(carId: Int?, price: Int?, userId: String?): RentCarResponseParams {
        return if(carId != null && price != null && userId != null){
            rentService.changeCarPrice(carId, price, userId)
        }else{
            return RentCarResponseParams(
                false,
                "Please insert valid query params", null
            )
        }
    }

    override suspend fun getCarStatus(carId: Int?): String {
        return rentService.getCarStatus(carId)
    }
}