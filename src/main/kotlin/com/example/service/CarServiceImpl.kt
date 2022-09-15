package com.example.service

import com.example.db.entities.CarImagesEntity
import com.example.db.entities.CarTable
import com.example.db.entities.UserTable
import com.example.models.*
import com.example.use_cases.CheckUserRole
import org.ktorm.database.Database
import org.ktorm.dsl.*

class CarServiceImpl : CarService {

    override val dbConnection: Database
        get() = super.dbConnection
    private val checkUserRole = CheckUserRole()

    override suspend fun insertCar(insertCarParams: InsertCarRequestParams): CarResponse {
        var carImages : Int? = null
        val role = checkUserRole.check(insertCarParams.userId)
        if(role != "admin"){
            return CarResponse(
                false,
                "The user is not admin, thus it cant add a car",
                null
            )
        }



        val alreadyExistingCar = dbConnection.from(CarTable).select()
            .where {
                CarTable.car_Model eq insertCarParams.car_Model
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
            }.singleOrNull()

        if(alreadyExistingCar != null){
            return CarResponse(
                false,
                "A car with that model exists",
                null
            )
        }


        val insertCar = dbConnection.insert(CarTable){
            set(CarTable.car_Brand, insertCarParams.car_Brand)
            set(CarTable.car_Model, insertCarParams.car_Model)
            set(CarTable.car_Type, insertCarParams.car_Type)
            set(CarTable.total_price, insertCarParams.totalPrice)
            set(CarTable.status, insertCarParams.status)
        }

        val carId = dbConnection.from(CarTable).select()
            .where {
                CarTable.car_Model eq insertCarParams.car_Model
            }.map {
                it[CarTable.id]
            }.singleOrNull()

        if(carId != null){
            insertCarParams.carImage.forEach {carImage ->
                    carImages = dbConnection.insert(CarImagesEntity){
                    set(it.image_url, carImage.imageUrl)
                    set(it.car_id, carId)
                }
            }
        }

        if(insertCar == 1 && carImages!! > 0){
            return CarResponse(
                true,
                "Car has been successfully added",
                null
            )
        }

        return CarResponse(
            false,
            "Something went wrong adding the car, try again",
            null
        )

    }

    override suspend fun deleteCar(carId: Int?, userId: String): CarResponse {
        if(carId != null){

            val role = checkUserRole.check(userId)
            if(role != "admin"){
                return CarResponse(
                    false,
                    "The user is not admin, thus it cant delete a car",
                    null
                )
            }

            val car = dbConnection.delete(CarTable){
                it.id eq carId
            }
            val carImage = dbConnection.delete(CarImagesEntity){
                it.car_id eq carId
            }
            return if(carImage == 1 && car == 1){
                CarResponse(
                    true,
                    "Successfully deleted the car",
                    null
                )
            }else{
                CarResponse(
                    false,
                    "Failed to delete car or the car has been already deleted",
                    null
                )
            }
        }
        return CarResponse(
            false,
            "Please enter a valid id",
            null
        )
    }

    override suspend fun getCarById(carId: Int?): CarResponse {
        if(carId != null){
            val car = dbConnection.from(CarTable).select()
                .where{
                    CarTable.id eq carId
                }.map {
                    val id  = it[CarTable.id]!!
                    val carBrand = it[CarTable.car_Brand]!!
                    val carModel = it[CarTable.car_Model]!!
                    val carType = it[CarTable.car_Type]!!
                    val totalPrice = it[CarTable.total_price]!!
                    val carStatus = it[CarTable.status]!!

                    Car(
                        id,
                        carBrand,
                        carModel,
                        carType,
                        totalPrice,
                        carStatus,
                        getCarImages(id)
                    )
                }

            return if(car.isNotEmpty()){
                CarResponse(
                    true,
                    "car",
                    car
                )
            }else{
                CarResponse(
                    false,
                    "That car doesnt exist",
                    null
                )
            }

        }
        return CarResponse(
            false,
            "Please enter a valid id",
            null
        )
    }

    override suspend fun getCars(): CarResponse {

        val cars = dbConnection.from(CarTable).select()
            .map {

                val id  = it[CarTable.id]!!
                val carBrand = it[CarTable.car_Brand]!!
                val carModel = it[CarTable.car_Model]!!
                val carType = it[CarTable.car_Type]!!
                val totalPrice = it[CarTable.total_price]!!
                val carStatus = it[CarTable.status]!!

                Car(
                    id,
                    carBrand,
                    carModel,
                    carType,
                    totalPrice,
                    carStatus,
                    getCarImages(id)
                )
            }

        return CarResponse(
            true,
            "cars",
            cars
        )

    }

    override suspend fun getCarImages(carId: Int): List<CarImage> {
        val carImages = dbConnection.from(CarImagesEntity).select()
            .where {
                CarImagesEntity.car_id eq carId
            }
            .map { images ->
                CarImage(
                    images[CarImagesEntity.id]!!,
                    images[CarImagesEntity.image_url]!!,
                    images[CarImagesEntity.car_id]!!
                )
            }
        return carImages
    }

    override suspend fun getCarByType(type: String): List<Car> {
        return  dbConnection.from(CarTable).select()
            .where{
                CarTable.car_Type eq type
            }.map {
                val id  = it[CarTable.id]!!
                val carBrand = it[CarTable.car_Brand]!!
                val carModel = it[CarTable.car_Model]!!
                val carType = it[CarTable.car_Type]!!
                val totalPrice = it[CarTable.total_price]!!
                val carStatus = it[CarTable.status]!!

                Car(
                    id,
                    carBrand,
                    carModel,
                    carType,
                    totalPrice,
                    carStatus,
                    getCarImages(id)
                )
            }
    }


}