package com.user.data.api

import com.user.data.modelfromservice.AllArticleOfPerson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("articles")
    suspend fun getAllArticleOfPerson(
        @Query("author")
         author:String="ali1748",
        @Query("page")
        pageNumber:Int=1

    ):Response<AllArticleOfPerson>
}