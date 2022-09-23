package com.example.service

import com.example.db.Database
import com.example.models.*

interface AdminService {

    val dbConnection: org.ktorm.database.Database
        get() = Database.connect()

    suspend fun addALocation(params: LocationModelRequestParams): LocationModelResponseParams


}