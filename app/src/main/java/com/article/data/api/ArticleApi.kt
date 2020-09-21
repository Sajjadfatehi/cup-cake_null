package com.article.data.api

import com.article.data.modelfromservice.ArticleResponse
import com.article.data.modelfromservice.CreateArticleModel
import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.Article
import com.user.data.modelfromservice.EditArticleRequest
import retrofit2.Response
import retrofit2.http.*

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


    @PUT("articles/{slug}")
    suspend fun updateArticle(
        @Path("slug")
        slug: String,
        @Body
        editArticleRequest: EditArticleRequest
    ): Response<Article>



    @GET("articles/{slug}")
    suspend fun articleBySlug(
        @Path("slug")
        slug:String
    ):Response<ArticleResponse>

}