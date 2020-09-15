package com.user.data

import com.core.LocalDataSource
import com.core.db.AppDataBase
import com.core.services.RetrofitService
import com.user.data.api.UserApi
import com.user.data.modelfromservice.RegisterRequest
import com.user.ui.ArticleView

class UserRepository(val db:AppDataBase) {
    val localDataSource = LocalDataSource()
    val retrofit = RetrofitService.retrofit.create(UserApi::class.java)

    init {


    }

    fun getPostInProf(): MutableList<ArticleView> {
        return localDataSource.getPostInProf()
    }

    suspend fun getAllArticleOfPerson(author: String, pageNumber: Int) =
        retrofit.getAllArticleOfPerson(author, pageNumber)


    suspend fun register(registerRequest: RegisterRequest) =
        retrofit.register(registerRequest)

    suspend fun favoriteArticle(slug: String) =
        retrofit.favoriteArticle(slug)

}