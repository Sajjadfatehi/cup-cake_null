package com.article.data

import com.core.LocalDataSource
import com.home.ui.PersonArticleModelView

class ArticleRepository {
    val localDataSource = LocalDataSource()

    init {

    }

    fun getTagTitleList(): MutableList<PersonArticleModelView> {

        return localDataSource.getTagList()
    }


}
