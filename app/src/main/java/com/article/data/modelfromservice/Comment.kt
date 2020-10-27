package com.article.data.modelfromservice

data class Comment(
    val author: Author?,
    val body: String,
    val createdAt: String?,
    val id: String?
)