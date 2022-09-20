package com.example.Routing

import com.example.models.InsertCarRequestParams
import com.example.repository.CarsRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.carRoutes(repository: CarsRepository) {
   routing {
       route("/cars"){

           authenticate {
               post("/addCar") {
                   val car = call.receive<InsertCarRequestParams>()
                   val result = repository.insertCar(car)
                   call.respond(result)
               }
               delete("/deleteCar/{id}/{userId}") {
                   val carId = call.parameters["id"]?.toIntOrNull()
                   val userId = call.parameters["userId"]
                   val result = repository.deleteCar(carId, userId)
                   call.respond(result)
               }

               get("/userRents/{userId}"){
                   val userId = call.parameters["userId"]
                   val result = repository.getUserRents(userId)
                   call.respond(result)
               }
               get("/rents"){
                   val result = repository.getAllRents()
                   call.respond(result)
               }
               get("/rents/rentByRentId/{rentId}"){
                   val rentId = call.parameters["rentId"]?.toLongOrNull()
                   val result = repository.getRentsByRentId(rentId)
                   call.respond(result)
               }

           }
           get("/allCars"){
               val cars = repository.getCars()
               call.respond(cars)
           }
           get("/car/{id}"){
               val carId = call.parameters["id"]?.toIntOrNull()
               val result = repository.getCarById(carId)
               call.respond(result)
           }
           get("/carType/{carType}"){
               val type = call.parameters["carType"].toString()
               val result = repository.getCarByType(type)
               call.respond(result)
           }



       }

   }
}