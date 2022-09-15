package com.example.Routing

import com.example.models.ChangeUserNameAndLastnameRequestParams
import com.example.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutes(userRepo: UserRepository){
    routing {
        route("/user"){
            authenticate {
                get("/userData/{userId}"){
                    val userId = call.parameters["userId"]
                    val result = userRepo.getUserData(userId)
                    call.respond(result)
                }
                put("/userData/change/name-lastname"){
                    val params = call.receive<ChangeUserNameAndLastnameRequestParams>()
                    val result = userRepo.changeUserData(params)
                    call.respond(result)
                }
            }

        }
    }
}