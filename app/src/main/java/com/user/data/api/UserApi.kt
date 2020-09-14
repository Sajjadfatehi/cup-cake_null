package com.user.data.api

import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("articles")
    suspend fun getAllArticleOfPerson(
        @Query("author")
         author:String="ali1748",
        @Query("page")
        pageNumber:Int=1

    ):Response<AllArticleOfPerson>

    @POST("users")
    suspend fun  register(@Body
        registerRequest: RegisterRequest
    ):Response<RegisterResponse>
}