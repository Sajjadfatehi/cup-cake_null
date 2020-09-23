package com.article.data.localdatasource

import com.article.data.ArticleDataEntity
import com.article.data.TagAndArticleEntity
import com.article.data.TagModel
import com.core.db.AppDataBase
import com.user.data.UserEntity

class ArticleLocalDataSource(val db :AppDataBase) {

    suspend fun updateArticle(articleDataEntity: ArticleDataEntity) {
        db.articleDao().update(articleDataEntity)
    }

    suspend fun insertArticle(articleDataEntity: List<ArticleDataEntity>) {
        db.articleDao().insert(articleDataEntity)
    }

    fun addUsers(userEntity: List<UserEntity>) {
        db.userDao().insertUser(userEntity)
    }

    fun addArticleTag(tagAndArticleEntity: List<TagAndArticleEntity>) {
        db.articleDao().insertArticleTag(tagAndArticleEntity)
    }

    suspend fun insertTags(tags: List<TagModel>) {
        db.tagDao().insertTags(tags)
    }




}