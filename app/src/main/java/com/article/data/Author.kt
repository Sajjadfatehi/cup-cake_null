package com.article.data

data class Author(
    val following: Boolean,
    val image: String,
    val username: String,
    val bio: String? = null
)