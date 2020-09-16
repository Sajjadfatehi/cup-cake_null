package com.article.data

import retrofit2.Response
import retrofit2.http.GET


/**
 * Created by moha on 9/15/2020.
 */

interface ArticleApi {
    @GET("tags")
    suspend fun getAllTags():Response<TagResponse>
}