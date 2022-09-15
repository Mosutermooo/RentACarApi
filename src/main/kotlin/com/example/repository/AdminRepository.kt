package com.example.repository

import com.example.models.RentCarResponseParams

interface AdminRepository {
    suspend fun allRents(userId: String?) : RentCarResponseParams
}