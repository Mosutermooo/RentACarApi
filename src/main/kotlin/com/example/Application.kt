package com.example


import com.example.Routing.*
import com.example.repository.*
import com.example.utils.JobSchedulerManager
import com.example.utils.TokenManager
import com.typesafe.config.ConfigFactory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import javax.swing.text.AbstractDocument.Content

fun main() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    embeddedServer(Netty, port = 8080) {
        //JobSchedulerManager().startScheduler()

        install(Authentication){
            jwt {
                verifier(TokenManager(config).verifyJWTToken())
                realm = config.property("realm").getString()
                validate {
                    if(it.payload.getClaim("username").asString().isNotEmpty()){
                        JWTPrincipal(it.payload)
                    }else{
                        null
                    }
                }
            }
        }

        install(ContentNegotiation){
            json()
        }

        authRoutes(UserRepositoryImpl())
        carRoutes(CarsRepositoryImpl())
        rentRoutes(RentRepositoryImpl())
        userRoutes(UserRepositoryImpl())
        adminRoutes(AdminRepositoryImpl())

    }.start(wait = true)
}
