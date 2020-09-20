package com.article.data

import androidx.room.withTransaction
import com.core.ResultCallBack
import com.core.RoomDataBase

class ArticleRepository(
) {

    val localDataSource = HomeLocalDataSource()
    val remoteDataSource = HomeRemoteDataSource()
    suspend fun getTagTitleList(): List<TagModel> {
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


}
