package com.article.data.modelfromservice

data class ArticleInCreateArticleModel(
    val body: String,
    val description: String,
    val tagList: List<String>,
    val title: String
)