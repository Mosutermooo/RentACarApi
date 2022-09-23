package com.example.repository

import com.example.models.LocationModelRequestParams
import com.example.models.LocationModelResponseParams
import com.example.models.RentCarResponseParams

interface AdminRepository {
    suspend fun addALocation(params: LocationModelRequestParams): LocationModelResponseParams
}