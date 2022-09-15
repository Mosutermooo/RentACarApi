package com.example.Routing

import com.example.models.LoginParams
import com.example.models.RegisterUserParams
import com.example.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRoutes(repository: UserRepository){
    routing {
        route("/auth"){
            post("/register") {
                val params = call.receive<RegisterUserParams>()
                val result = repository.registerUser(params)
                call.respond(result)
            }
            post("/login") {
                val params = call.receive<LoginParams>()
                val result = repository.loginUser(params)
                call.respond(result)
            }
            get("/refreshToken"){
                val oldToken = call.parameters["oldToken"].toString()
                val result = repository.refreshToken(oldToken)
                call.respond(result)
            }
        }
    }
}