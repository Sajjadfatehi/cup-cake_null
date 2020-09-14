package com.article.data.api

import com.user.data.modelfromservice.AllArticleOfPerson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("articles")
    suspend fun getArticlesByTag(
        @Query("tag")
        tag:String,
        @Query("page")
        pageNumber:Int=1

    ):Response<AllArticleOfPerson>
}