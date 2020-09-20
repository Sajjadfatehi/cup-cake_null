package com.user.data.api

import com.user.data.modelfromservice.*
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


    @DELETE("articles/{slug}/favorite")
    suspend fun unFavoriteArticle(
        @Path("slug")
        slug: String

    ): Response<Article>


    @GET("articles")
    suspend fun favoriteArticleByUserName(
        @Query("favorited")
        favorited: String
    ): Response<AllArticleOfPerson>

    @DELETE("articles/{slug}")
    suspend fun deleteArticle(
        @Path("slug")
        slug: String

    ): Response<Unit>

    @GET("profiles/{USERNAME}")
    suspend fun profile(
        @Path("USERNAME")
        userName: String
    ): Response<Profile>

    @POST("profiles/{USERNAME}/follow")
    suspend fun follow(
        @Path("USERNAME")
        userName:String,
        @Body
        user: FollowRequest
    ):Response<Profile>

    @DELETE("profiles/{USERNAME}/follow")
    suspend fun unFollow(
        @Path("USERNAME")
        userName:String
    ):Response<Profile>
}