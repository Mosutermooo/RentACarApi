package com.example.Routing

import com.example.models.RentCarRequestParams
import com.example.repository.RentCarRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.rentRoutes(repository: RentCarRepository){
    routing {
        route("/rent"){
            authenticate {
                post ("/car"){
                    val params = call.receive<RentCarRequestParams>()
                    val result = repository.rentACar(params)
                    call.respond(result)
                }
                get("/carStatus"){
                    val carId = call.request.queryParameters["carId"]?.toIntOrNull()
                    val result = repository.getCarStatus(carId)
                    call.respond(result)
                }
                put ("/changeCarStatus"){
                    val carId = call.request.queryParameters["carId"]?.toIntOrNull()
                    val status = call.request.queryParameters["status"]
                    val result = repository.changeCarStatus(carId, status)
                    call.respond(result)
                }
                put ("/changeCarPrice"){
                    val carId = call.request.queryParameters["carId"]?.toIntOrNull()
                    val price = call.request.queryParameters["price"]?.toIntOrNull()
                    val userId = call.request.queryParameters["userId"]
                    val result = repository.changeCarPrice(carId, price, userId)
                    call.respond(result)
                }

            }

        }
    }
}