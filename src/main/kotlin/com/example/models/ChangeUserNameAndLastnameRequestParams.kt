package com.example.models

data class ChangeUserNameAndLastnameRequestParams(
    val userId: String,
    val name: String,
    val lastname: String
)