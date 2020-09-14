package com.article.data

import com.article.data.api.ArticleApi
import com.core.LocalDataSource
import com.core.db.AppDataBase
import com.core.services.RetrofitService
import com.home.ui.PersonArticleModelView

class ArticleRepository(val db:AppDataBase) {
    val localDataSource = LocalDataSource()

    init {

    }

    suspend fun getArticleByTag(tag:String,pageNumber:Int)=
        RetrofitService.retrofit.create(ArticleApi::class.java).getArticlesByTag(tag,pageNumber)


    fun getTagTitleList(): MutableList<PersonArticleModelView> {

        return localDataSource.getTagList()
    }


}
