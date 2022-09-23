package com.example.Routing

import com.example.models.LocationModelRequestParams
import com.example.repository.AdminRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.adminRoutes(adminRepository: AdminRepository){

    routing {
        route("/admin"){
           authenticate {
               post("/addLocation"){
                   val params = call.receive<LocationModelRequestParams>()
                   val result = adminRepository.addALocation(params)
                   call.respond(result)
               }
           }
        }
    }

}