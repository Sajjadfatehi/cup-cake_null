package com.article.data

import androidx.room.withTransaction
import com.article.data.api.ArticleApi
import com.article.data.modelfromservice.CreateArticleModel
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.core.RoomDataBase
import com.user.data.modelfromservice.EditArticleRequest

class ArticleRepository() {

    val localDataSource = HomeLocalDataSource()
    val remoteDataSource = HomeRemoteDataSource()
    val retrofit = RetrofitUtil.getInstance().create(ArticleApi::class.java)

    suspend fun getTagTitleList0(): List<TagModel> {
        val result = remoteDataSource.getAllTags()
        if (result is ResultCallBack.Success) {
            localDataSource.addAllTags(result.data.tags.map { TagModel(it) })
        }
        return localDataSource.getTagList()
    }

    suspend fun getArticleWithTag(text: String): List<ArticleUser> {
        val result = remoteDataSource.getArticleWithTag(text)


        if (result is ResultCallBack.Success) {
            RoomDataBase.getDB().withTransaction {
                localDataSource.addUsers(result.data.articles.map {
                    it.mapToUserEntity()
                })

                localDataSource.addArticle(result.data.articles.map {
                    it.mapToEntity()
                })
            }

            localDataSource.addArticleTag(result.data.articles.map {
                TagAndArticleEntity(text,it.slug)
            })

        }
        return localDataSource.getArticleWithTag(text)
    }

    suspend fun getArticleByTag(tag: String, pageNumber: Int) =
        retrofit.getArticlesByTag(tag, pageNumber)

    suspend fun createArticle(createArticleModel: CreateArticleModel) =
        retrofit.createArticle(createArticleModel = createArticleModel)


    suspend fun updateArticle(slug: String, editArticleRequest: EditArticleRequest) =
        retrofit.updateArticle(slug, editArticleRequest)

}
