package com.user.data.modelfromservice

data class AllArticleOfPerson(
    val articles: MutableList<Article>,
    val articlesCount: Int
)