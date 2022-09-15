package com.example.repository

import com.example.models.*

interface UserRepository {
    suspend fun registerUser(params: RegisterUserParams): RegisterResponse
    suspend fun loginUser(params: LoginParams): LoginResponse
    suspend fun refreshToken(token: String): RefreshTokenResponse
    suspend fun getUserData(userId: String?): UserResponseParams
    suspend fun changeUserData(params: ChangeUserNameAndLastnameRequestParams): UserResponseParams
}