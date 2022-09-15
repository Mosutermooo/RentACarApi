package com.example.service

import com.example.db.entities.CarImagesEntity
import com.example.db.entities.CarTable
import com.example.db.entities.RentTable
import com.example.db.entities.UserTable
import com.example.models.*
import com.example.use_cases.CheckUserRole
import org.ktorm.database.Database
import org.ktorm.dsl.*

class AdminServiceImpl : AdminService {

    override val dbConnection: Database
        get() = super.dbConnection

    private val checkUserRole = CheckUserRole()

    override suspend fun allRents(userId: String?): AllRentsResponseParams {
        userId ?: return AllRentsResponseParams(
            false,
            "Invalid userId",
            null,
            null,
            null,
            null,
            null,
        )

        val role = checkUserRole.check(userId)
        if(role != "admin"){
            return AllRentsResponseParams(
                false,
                "The user is not admin, cant get all rents",
                null,
                null,
                null,
                null,
                null,
            )
        }

        var cars: List<Car> = emptyList()
        var user: List<User> = emptyList()

        val rents = dbConnection.from(RentTable).select()
            .map {
                val carId = it[RentTable.carId]!!
                val userId2 = it[RentTable.userId]!!
                val price = it[RentTable.price]!!
                val rentTime = it[RentTable.rentTime]!!
                val rentId = it[RentTable.rentId]!!
                Rent(
                    carId,
                    userId2,
                    price,
                    rentTime,
                    rentId
                )
            }

        rents.forEach {
            cars = dbConnection.from(CarTable).select()
                .where {
                    CarTable.id eq it.carId
                }
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

             user = dbConnection.from(UserTable).select()
                 .where {
                     UserTable.userId eq it.userId
                 }
                .map {
                    val userId2 = it[UserTable.userId]!!
                    val username = it[UserTable.username]!!
                    val email = it[UserTable.email]!!
                    val userPassword = it[UserTable.password]!!
                    val name = it[UserTable.name]!!
                    val lastname = it[UserTable.lastname]!!
                    val createdAt = it[UserTable.createdAt]!!
                    val role2 = it[UserTable.role]!!
                    User(
                        userId2,
                        name,
                        lastname,
                        email,
                        username,
                        createdAt,
                        userPassword,
                        role2
                    )
                }

        }

        rents.groupBy {rent ->
            user.groupBy {
                it.userId to rent.userId
            }
            cars.groupBy {
                it.id to rent.carId
            }
        }






        TODO("Not yet implemented")




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



}