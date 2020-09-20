package com.article.data

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by moha on 9/15/2020.
 */

interface ArticleApi {
    @GET("tags")
    suspend fun getAllTags(): Response<TagResponse>

    @GET("articles")
    suspend fun getArticleWithTag(@Query("tag") tag: String): Response<ArticleModel>
}