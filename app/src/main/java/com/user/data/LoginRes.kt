package com.user.data

data class LoginRes (
    val user: LoginUserModel
){

    class LoginUserModel(
        val username: String,
        val email: String,
        val token: String
    )
}

