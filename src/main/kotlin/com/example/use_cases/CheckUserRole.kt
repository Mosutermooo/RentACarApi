package com.example.use_cases

import com.example.db.Database
import com.example.db.entities.UserTable
import com.example.models.CarResponse
import com.example.models.User
import org.ktorm.dsl.*

class CheckUserRole {
    private val dbConnection = Database.connect()
    fun check(userId: String) : String {
        val user = dbConnection.from(UserTable).select()
            .where {
                UserTable.userId eq userId
            }.map {
                val userId2 = it[UserTable.userId]!!
                val username = it[UserTable.username]!!
                val email = it[UserTable.email]!!
                val userPassword = it[UserTable.password]!!
                val name = it[UserTable.name]!!
                val lastname = it[UserTable.lastname]!!
                val createdAt = it[UserTable.createdAt]!!
                val role = it[UserTable.role]!!
                User(
                    userId2,
                    name,
                    lastname,
                    email,
                    username,
                    createdAt,
                    userPassword,
                    role
                )
            }.singleOrNull() ?: return  "The user adding the car does not exist"

        return user.role
    }
}