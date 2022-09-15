package com.example.db.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar


object UserTable : Table<Nothing>("users") {

    val userId = varchar("userId")
    val name = varchar("name")
    val lastname = varchar("lastname")
    val email = varchar("email")
    val username = varchar("username")
    val createdAt = long("createdAt")
    val password = varchar("password")
    val role = varchar("role")

}