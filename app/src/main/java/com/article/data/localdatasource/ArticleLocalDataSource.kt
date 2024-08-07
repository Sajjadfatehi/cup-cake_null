package com.article.data.localdatasource

import androidx.room.withTransaction
import com.article.data.ArticleDataEntity
import com.article.data.ArticleUser
import com.article.data.TagAndArticleEntity
import com.article.data.TagModel
import com.article.data.modelfromservice.UserAndHisFavoriteArticle
import com.config.MyApp
import com.core.db.AppDataBase
import com.storage.data.PreferenceProperty.Companion.getPreferences
import com.storage.data.Settings
import com.user.data.UserEntity

class ArticleLocalDataSource(val db: AppDataBase) {
    private val settings = Settings(MyApp.app.applicationContext.getPreferences())

    suspend fun updateArticle(articleDataEntity: ArticleDataEntity) {
        db.articleDao().update(articleDataEntity)
    }

    suspend fun insertArticle(articleDataEntity: List<ArticleDataEntity>) {
        db.articleDao().insert(articleDataEntity)
    }

    suspend fun addArticleTag(tagAndArticleEntity: List<TagAndArticleEntity>) {
        db.withTransaction {
            db.articleDao().insertArticleTag(tagAndArticleEntity)
        }
    }

    suspend fun insertTags(tags: List<TagModel>) {
        db.withTransaction {
            db.tagDao().insertTags(tags)

        }
    }

    suspend fun addUsers(userEntity: List<UserEntity>) {
        db.withTransaction {
            db.userDao().insertUser(userEntity)
        }
    }

    suspend fun addArticle(articles: List<ArticleDataEntity>) {
        db.withTransaction {
            articles.forEach {
                db.articleDao().insertOneByOne(it)
            }
        }
    }

    suspend fun getArticleByTag(tag: String): List<ArticleUser> {
        return db.articleDao().getArticleWithTag(tag)
    }


    suspend fun deleteUserAndFavoriteArticles(userAdFavoriteArticles: List<UserAndHisFavoriteArticle>) {
        db.userDao().insertUserAndFavoriteArticle(userAdFavoriteArticles)

    }

    suspend fun addUserAndFavoriteArticles(userAdFavoriteArticles: List<UserAndHisFavoriteArticle>) {
        db.userDao().insertUserAndFavoriteArticle(userAdFavoriteArticles)

    }


    fun getUserNameFromShare() = settings.username.toString()


    fun saveTitle(title: String) {
        settings.title = title
    }

    fun saveBody(body: String) {
        settings.body = body
    }

    fun getTitleFromShare() = settings.title

    fun getBodyFromShare() = settings.body

}