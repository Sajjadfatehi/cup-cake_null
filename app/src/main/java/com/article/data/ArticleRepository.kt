package com.article.data

import com.core.ResultCallBack

class ArticleRepository(
) {

    val localDataSource = HomeLocalDataSource()
    val remoteDataSource = HomeRemoteDataSource()
    suspend fun getTagTitleList(): List<TagModel> {
        val result = remoteDataSource.getAllTags()
        if (result is ResultCallBack.Success) {
            localDataSource.addAllTags(result.data.tags.map { TagModel(it) })
        }


        localDataSource.getTagList()
        return localDataSource.getTagList()
    }


}
