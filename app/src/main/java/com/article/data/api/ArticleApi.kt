package com.article.data.api

import com.article.data.modelfromservice.CreateArticleModel
import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.Article
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ArticleApi {

    @GET("articles")
    suspend fun getArticlesByTag(
        @Query("tag")
        tag: String,
        @Query("page")
        pageNumber: Int = 1

    ): Response<AllArticleOfPerson>

    @POST("articles")
    suspend fun createArticle(
        @Body

        createArticleModel: CreateArticleModel
    ): Response<Article>


}