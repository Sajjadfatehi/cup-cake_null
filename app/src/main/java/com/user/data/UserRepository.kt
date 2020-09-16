package com.user.data

import com.article.data.HomeLocalDataSource
import com.user.ui.ArticleView

class UserRepository {
    val localDataSource = HomeLocalDataSource()

    init {

    }

    fun getPostInProf(): MutableList<ArticleView> {
        return localDataSource.getPostInProf()
    }


}