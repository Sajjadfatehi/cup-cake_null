package com.user.data

import com.core.LocalDataSource
import com.user.ui.ArticleView

class UserRepository {
    val localDataSource = LocalDataSource()

    init {

    }

    fun getPostInProf(): MutableList<ArticleView> {
        return localDataSource.getPostInProf()
    }

}