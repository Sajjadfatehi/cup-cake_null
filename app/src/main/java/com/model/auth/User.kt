package com.part.myapplication.models

data class User(
    val email: String,
    val token: String? = null,
    val password: String? = null,
    val username: String? = null
)