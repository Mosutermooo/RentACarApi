package com.example.service

import com.example.db.Database
import com.example.models.*

interface UserService {

    val dbConnection: org.ktorm.database.Database
        get() = Database.connect()

    suspend fun registerUser(params: RegisterUserParams) : Boolean
    suspend fun loginUserService(params: LoginParams) : LoginResponse
    suspend fun findUserByUsername(username: String) : User?
    suspend fun hashPassword(password: String): String
    suspend fun refreshToken(token: String): RefreshTokenResponse
    suspend fun getUserData(userId: String?): UserResponseParams
    suspend fun changeUserData(params: ChangeUserNameAndLastnameRequestParams): UserResponseParams

    suspend fun getAllLocations(): LocationModelResponseParams
    suspend fun getSingleLocation(id: Int?): LocationModelResponseParams
}