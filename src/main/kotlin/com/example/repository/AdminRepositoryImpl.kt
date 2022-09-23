package com.example.repository

import com.example.models.LocationModelRequestParams
import com.example.models.LocationModelResponseParams
import com.example.models.RentCarResponseParams
import com.example.service.AdminServiceImpl

class AdminRepositoryImpl: AdminRepository {

    private val adminService = AdminServiceImpl()
    override suspend fun addALocation(params: LocationModelRequestParams): LocationModelResponseParams {
        return adminService.addALocation(params)
    }


}