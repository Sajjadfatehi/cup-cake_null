package com.user.data.api

import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.Article
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("articles")
    suspend fun getAllArticleOfPerson(
        @Query("author")
        author: String,
        @Query("page")
        pageNumber: Int = 1

    ): Response<AllArticleOfPerson>

    @POST("users")
    suspend fun register(
        @Body
        registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("articles/{slug}/favorite")
    suspend fun favoriteArticle(
        @Path("slug")
        slug: String

    ): Response<Article>
}