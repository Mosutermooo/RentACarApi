package com.example.service

import com.example.db.entities.CarTable
import com.example.db.entities.RentTable
import com.example.models.Car
import com.example.models.CarResponse
import com.example.models.RentCarRequestParams
import com.example.models.RentCarResponseParams
import com.example.use_cases.CheckUserRole
import com.example.utils.Constants
import com.example.utils.JobSchedulerManager
import com.example.utils.RentJob
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder

class RentCarServiceImpl : RentCarService{

    override val dbConnection: Database
        get() = super.dbConnection

    private val checkUserRole = CheckUserRole()

    override suspend fun rentACar(params: RentCarRequestParams): RentCarResponseParams {

        val rentId = generateRentId()

        val isCarAvailable = dbConnection.from(CarTable).select()
            .where {
                params.carId eq CarTable.id
            }.map{
                it[CarTable.status]
            }.singleOrNull()
            ?: return RentCarResponseParams(
                false,
                "This car doesnt exists",
                null
            )
        if(isCarAvailable == "unavailable"){
            return RentCarResponseParams(
                false,
                "This car is not available at the moment",
                null
            )
        }


        val updateCarStatus = dbConnection.update(CarTable){
            where {
                CarTable.id eq params.carId
            }
            set(it.status, "unavailable")
        }

        val addCarToRentTable = dbConnection.insert(RentTable){
            set(it.carId, params.carId)
            set(it.userId, params.userId)
            set(it.price, params.price)
            set(it.rentTime, params.rentTime)
            set(it.rentId, rentId)
        }
        if(updateCarStatus == 1 && addCarToRentTable == 1){
            //startJobScheduler(params.carId, params.rentTime)
            return RentCarResponseParams(
                true,
                "Car rented, show your rent id to pick up the car",
                rentId
            )

        }

        return RentCarResponseParams(
            false,
            "Something went wrong renting the car",
            null
        )


    }

    private fun startJobScheduler(carId: Int?, updateIn: Long) {
        val job: JobDetail = JobBuilder.newJob(RentJob::class.java)
            .withIdentity(Constants.jobId, Constants.WATCH_JOB_GROUP)
            .usingJobData(Constants.JOB_MAP_CarId_ID_KEY, carId)
            .usingJobData(Constants.JOB_MAP_UPDATE_IN_ID_KEY, updateIn)
            .build()
        val trigger : Trigger = TriggerBuilder.newTrigger()
            .withIdentity(Constants.triggerId, Constants.WATCH_JOB_GROUP)
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMilliseconds(updateIn)
            ).build()
        JobSchedulerManager().scheduler.scheduleJob(job, trigger)
    }

    override suspend fun generateRentId(): Long {
        return System.currentTimeMillis()
    }



    override suspend fun changeCarStatus(carId: Int, newStatus: String): RentCarResponseParams {
           dbConnection.from(CarTable).select()
            .where {
                CarTable.id eq carId
            }.map {
                val id = it[CarTable.id]!!
                val carBrand = it[CarTable.car_Brand]!!
                val carModel = it[CarTable.car_Model]!!
                val carType = it[CarTable.car_Type]!!
                val totalPrice = it[CarTable.total_price]!!
                val status = it[CarTable.status]!!
                Car(
                    id,
                    carBrand,
                    carModel,
                    carType,
                    totalPrice,
                    status,
                    emptyList()
                )
            }.singleOrNull() ?: return RentCarResponseParams(
                false,
                "Car does not exists",
                null
            )


        val updateCarStatus = dbConnection.update(CarTable){
            where {
                it.id eq carId
            }
            set(it.status, newStatus)
        }

        if(updateCarStatus == 1){
            return RentCarResponseParams(
                true,
                "Car status has been changed to $newStatus",
                null
            )
        }
        return RentCarResponseParams(
            false,
            "Something went wrong changing car status, try again",
            null
        )
    }

    override suspend fun changeCarPrice(carId: Int, price: Int, userId: String): RentCarResponseParams {

        val role = checkUserRole.check(userId)
        if(role != "admin"){
            return RentCarResponseParams(
                false,
                "The user is not admin, thus it cant add a car",
                null
            )
        }

        dbConnection.from(CarTable).select()
            .where {
                CarTable.id eq carId
            }.map {
                val id = it[CarTable.id]!!
                val carBrand = it[CarTable.car_Brand]!!
                val carModel = it[CarTable.car_Model]!!
                val carType = it[CarTable.car_Type]!!
                val totalPrice = it[CarTable.total_price]!!
                val status = it[CarTable.status]!!
                Car(
                    id,
                    carBrand,
                    carModel,
                    carType,
                    totalPrice,
                    status,
                    emptyList()
                )
            }.singleOrNull() ?: return RentCarResponseParams(
            false,
            "Car does not exists",
            null
        )

        val updateCarPrice = dbConnection.update(CarTable){
            where {
                it.id eq carId
            }
            set(it.total_price, price)
        }

        if(updateCarPrice == 1){
            return RentCarResponseParams(
                true,
                "Car price has been changed to $price",
                null
            )
        }
        return RentCarResponseParams(
            false,
            "Something went wrong changing car price, try again",
            null
        )

    }

    override fun updateCarStatus(carId: Int?, updateIn: Long) {

    }


}