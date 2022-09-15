package com.example.Routing

import com.example.repository.AdminRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.adminRoutes(adminRepository: AdminRepository){

    routing {
        route("/admin"){
            get("/allRents"){
                val userId = call.parameters["userId"]
                val result = adminRepository.allRents(userId)
                call.respond(result)
            }
        }
    }

}