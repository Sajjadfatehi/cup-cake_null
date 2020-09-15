package com.user.data.modelfromservice

import java.io.Serializable

data class Author(
    val following: Boolean,
    val image: String?,
    val username: String
) : Serializable