package com.article.data

import com.article.data.api.ArticleApi
import com.article.data.modelfromservice.CreateArticleModel
import com.core.LocalDataSource
import com.core.db.AppDataBase
import com.core.services.RetrofitService
import com.home.ui.PersonArticleModelView

class ArticleRepository(val db:AppDataBase) {
    val localDataSource = LocalDataSource()
    val retrofit = RetrofitService.retrofit.create(ArticleApi::class.java)

    init {

    }

    suspend fun getArticleByTag(tag: String, pageNumber: Int) =
        retrofit.getArticlesByTag(tag, pageNumber)

    suspend fun createArticle(createArticleModel: CreateArticleModel) =
        retrofit.createArticle(createArticleModel = createArticleModel)


    fun getTagTitleList(): MutableList<PersonArticleModelView> {

        return localDataSource.getTagList()
    }


}
