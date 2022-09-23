package com.example.service

import com.example.db.entities.*
import com.example.models.*
import com.example.use_cases.CheckUserRole
import org.ktorm.database.Database
import org.ktorm.dsl.*

class AdminServiceImpl : AdminService {

    override val dbConnection: Database
        get() = super.dbConnection

    private val checkUserRole = CheckUserRole()


    override suspend fun addALocation(params: LocationModelRequestParams): LocationModelResponseParams {
        if(checkUserRole.check(params.userId) != "admin"){
            return LocationModelResponseParams(
                false,
                "This user is not permitted to add locations"
            )
        }

        val location = dbConnection.insert(LocationsTable){
            set(LocationsTable.lat, params.lat)
            set(LocationsTable.lng, params.lng)
            set(LocationsTable.location_name, params.location_name)
            set(LocationsTable.city, params.city)
        }
        return if(location == 1){
            LocationModelResponseParams(
                true,
                "Location Successfully added"
            )
        }else{
            LocationModelResponseParams(
                true,
                "Failed to add location, try again"
            )
        }

    }






}