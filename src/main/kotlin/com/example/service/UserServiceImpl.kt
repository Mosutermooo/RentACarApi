package com.example.service

import com.example.db.entities.UserTable
import com.example.models.*
import com.example.utils.TokenManager
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.mindrot.jbcrypt.BCrypt

class UserServiceImpl : UserService {

    private val tokenManager = TokenManager(HoconApplicationConfig(ConfigFactory.load()))

    override val dbConnection: Database
        get() = super.dbConnection

    override suspend fun registerUser(params: RegisterUserParams): Boolean {
        val hashPassword = hashPassword(password = params.password)
       val user = dbConnection.insert(UserTable){
            set(it.userId, generateUserId())
            set(it.email, params.email)
            set(it.username, params.username)
            set(it.password, hashPassword)
            set(it.createdAt, getCurrentMilliseconds())
            set(it.lastname, "")
            set(it.name, "")
            set(it.role, "user")
        }

        if(user == 1){
           return true
        }
        return false
    }

    override suspend fun loginUserService(params: LoginParams): LoginResponse {
       val user = dbConnection.from(UserTable).select()
           .where {
               UserTable.username eq params.username
           }.map {
               val userId = it[UserTable.userId]!!
               val username = it[UserTable.username]!!
               val email = it[UserTable.email]!!
               val userPassword = it[UserTable.password]!!
               val name = it[UserTable.name]!!
               val lastname = it[UserTable.lastname]!!
               val createdAt = it[UserTable.createdAt]!!
               val role = it[UserTable.role]!!
               User(
                   userId,
                   name,
                   lastname,
                   email,
                   username,
                   createdAt,
                   userPassword,
                   role
               )

           }.firstOrNull() ?: return LoginResponse(
               false,
               "Invalid credentials",
               null,
               null
           )

        val checkPass = BCrypt.checkpw(params.password, user.password)
        if(!checkPass){
            return LoginResponse(
                false,
                "Password doesnt match with our password in the database, try again",
                null,
                null
            )
        }

        val token = tokenManager.generateJWTToken(params)
        return  LoginResponse(
            true,
            "Logged in successfully",
            token,
            user.userId
        )


    }

    override suspend fun findUserByUsername(username: String): User? {
        val user = dbConnection.from(UserTable).select()
            .where {
                UserTable.username eq username
            }.map {
                val userId =it[UserTable.userId]!!
                val name = it[UserTable.name]!!
                val lastname = it[UserTable.lastname]!!
                val email = it[UserTable.email]!!
                val usedUsername = it[UserTable.username]!!
                val createdAt = it[UserTable.createdAt]!!
                val role = it[UserTable.role]!!
                User(
                    userId,
                    name,
                    lastname,
                    email,
                    usedUsername,
                    createdAt,
                    role = role
                )
            }.singleOrNull()
        return user
    }



    override suspend fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override suspend fun refreshToken(token: String): RefreshTokenResponse {
        val newToken = tokenManager.refreshJWTToken(token)
        if (newToken != null){
            return RefreshTokenResponse(
                newToken,
                "New Token"
            )
        }
        return RefreshTokenResponse(
            null,
            "Something went wrong generating the token"
        )
    }

    override suspend fun getUserData(userId: String?): UserResponseParams {
        userId ?: return UserResponseParams(
            false,
            "Please enter a valid userId",
            null
        )

        val user = dbConnection.from(UserTable).select()
            .where {
                UserTable.userId eq userId
            }.map {
                val userId2 =it[UserTable.userId]!!
                val name = it[UserTable.name]!!
                val lastname = it[UserTable.lastname]!!
                val email = it[UserTable.email]!!
                val usedUsername = it[UserTable.username]!!
                val createdAt = it[UserTable.createdAt]!!
                val role = it[UserTable.role]!!
                User(
                    userId2,
                    name,
                    lastname,
                    email,
                    usedUsername,
                    createdAt,
                    role = role
                )
            }.singleOrNull() ?: return UserResponseParams(
            false,
            "Invalid user, the user does not exist with that userId",
            null
            )

        return UserResponseParams(
            true,
            "User Data",
            user
        )



    }

    override suspend fun changeUserData(params: ChangeUserNameAndLastnameRequestParams): UserResponseParams {
        val user = dbConnection.update(UserTable){
            where {
                UserTable.userId eq params.userId
            }
            set(UserTable.name, params.name)
            set(UserTable.lastname, params.lastname)
        }
        if(user == 1){
            return UserResponseParams(
                true,
                "Successfully updated user data",
                null
            )
        }else{
            return UserResponseParams(
                false,
                "Something went wrong updating data, try again",
                null
            )
        }
    }

    private fun generateUserId(): String {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(20) { alphabet.random() }.joinToString("")
    }

    private fun getCurrentMilliseconds() : Long {
        return  System.currentTimeMillis()
    }

}