package com.user.data.modelfromservice

data class User(
    val email: String,
    val password: String?=null,
    val username: String,
    val token:String?=null
)