package com.example.repository

import com.example.models.RentCarResponseParams
import com.example.service.AdminServiceImpl

class AdminRepositoryImpl: AdminRepository {

    private val adminService = AdminServiceImpl()

    override suspend fun allRents(userId: String?): RentCarResponseParams {
        //return adminService.allRents(userId)
        TODO("Not yet implemented")
    }
}