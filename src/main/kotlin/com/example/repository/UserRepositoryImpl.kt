package com.example.repository

import com.example.models.*
import com.example.service.UserService
import com.example.service.UserServiceImpl

class UserRepositoryImpl : UserRepository {

    private val userService: UserService = UserServiceImpl()

    override suspend fun registerUser(params: RegisterUserParams): RegisterResponse {
        return if(userService.findUserByUsername(params.username) != null){
             RegisterResponse(
                false,
                "A user with that username already exists"
            )
        }else{
            if(userService.registerUser(params)){
                RegisterResponse(
                    true,
                    "Registering user is successful"
                )
            }else{
                RegisterResponse(
                    false,
                    "Something went wrong registering the user"
                )
            }
        }
    }

    override suspend fun loginUser(params: LoginParams): LoginResponse {
        return userService.loginUserService(params)
    }

    override suspend fun refreshToken(token: String): RefreshTokenResponse {
        return userService.refreshToken(token)
    }

    override suspend fun getUserData(userId: String?): UserResponseParams {
        return userService.getUserData(userId)
    }

    override suspend fun changeUserData(params: ChangeUserNameAndLastnameRequestParams): UserResponseParams {
        return userService.changeUserData(params)
    }
}