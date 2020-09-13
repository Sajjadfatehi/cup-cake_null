package com.user.data

import com.core.LocalDataSource
import com.core.db.AppDataBase
import com.core.services.RetrofitService
import com.user.data.api.UserApi
import com.user.ui.ArticleView

class UserRepository(val db:AppDataBase) {
    val localDataSource = LocalDataSource()

    init {


    }

    fun getPostInProf(): MutableList<ArticleView> {
        return localDataSource.getPostInProf()
    }

    suspend fun getAllArticleOfPerson(author:String,pageNumber:Int)=
        RetrofitService.retrofit.create(UserApi::class.java).getAllArticleOfPerson(author,pageNumber)


}