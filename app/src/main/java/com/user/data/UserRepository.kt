package com.user.data

import com.core.LocalDataSource
import com.core.db.AppDataBase
import com.user.ui.ArticleView

class UserRepository(val db:AppDataBase) {
    val localDataSource = LocalDataSource()

    init {

    }

    fun getPostInProf(): MutableList<ArticleView> {
        return localDataSource.getPostInProf()
    }

}