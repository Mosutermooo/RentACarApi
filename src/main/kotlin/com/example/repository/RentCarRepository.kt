package com.example.repository

import com.example.models.RentCarRequestParams
import com.example.models.RentCarResponseParams

interface RentCarRepository {
    suspend fun rentACar(params: RentCarRequestParams): RentCarResponseParams
    suspend fun changeCarStatus(carId: Int?, status: String?): RentCarResponseParams
    suspend fun changeCarPrice(carId: Int?, price: Int?, userId: String?): RentCarResponseParams

}