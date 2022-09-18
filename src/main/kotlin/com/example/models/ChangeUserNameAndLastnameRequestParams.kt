package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ChangeUserNameAndLastnameRequestParams(
    val userId: String,
    val name: String,
    val lastname: String
)