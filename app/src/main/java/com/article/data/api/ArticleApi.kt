package com.article.data.api

import com.article.data.ArticleModel
import com.article.data.TagResponse
import com.article.data.modelfromservice.ArticleResponse
import com.article.data.modelfromservice.CreateArticleModel
import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.EditArticleRequest
import retrofit2.Response
import retrofit2.http.*

interface ArticleApi {

    @GET("tags")
    suspend fun getAllTags(): Response<TagResponse>

    @GET("articles")
    suspend fun getArticleWithTag(@Query("tag") tag: String): Response<ArticleModel>

    @GET("articles")
    suspend fun getArticlesByTag(
        @Query("tag")
        tag: String,
        @Query("page")
        pageNumber: Int = 1

    ): Response<AllArticleOfPerson>

    @GET("articles")
    suspend fun getArticlesByTagNew(
        @Query("tag")
        tag: String,
        @Query("page")
        pageNumber: Int = 1

    ): Response<ArticleModel>

    @POST("articles")
    suspend fun createArticle(
        @Body createArticleModel: CreateArticleModel
    ): Response<ArticleResponse>


    @PUT("articles/{slug}")
    suspend fun updateArticle(
        @Path("slug") slug: String,
        @Body editArticleRequest: EditArticleRequest
    ): Response<ArticleResponse>


    @GET("articles/{slug}")
    suspend fun articleBySlug(
        @Path("slug")
        slug: String
    ): Response<ArticleResponse>

}