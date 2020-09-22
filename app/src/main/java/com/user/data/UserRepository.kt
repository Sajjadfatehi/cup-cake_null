package com.user.data

import android.util.Log
import androidx.room.withTransaction
import com.article.data.ArticleUser
import com.article.data.HomeLocalDataSource
import com.article.data.TagAndArticleEntity
import com.article.data.TagModel
import com.article.data.modelfromservice.UserAndHisFavoriteArticle
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.core.RoomDataBase
import com.user.data.api.UserApi
import com.user.data.localdatasource.UserLocalDataSource
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.reomtedatasource.UserRemote


class UserRepository(val local: UserLocalDataSource, val remote: UserRemote) {

    suspend fun getAllArticleOfPerson(author: String, pageNumber: Int) =
        remote.getAllArticleOfPerson(author, pageNumber)


    suspend fun register(registerRequest: RegisterRequest) =
        remote.register(registerRequest)

    suspend fun favoriteArticle(slug: String) =
        remote.favoriteArticle(slug)

    suspend fun unFavoriteArticle(slug: String) =
        remote.unFavoriteArticle(slug)


    suspend fun favoritedArticleByUserName(favoritedUserName: String) =
        remote.favoritedArticleByUserName(favoritedUserName)

    suspend fun deleteArticle(slug: String): ResultCallBack<Unit> {
        val result = remote.deleteArticle(slug)
        if (result is ResultCallBack.Success){
            local.deleteArticle(slug)

        }
        return result
    }

    suspend fun profile(userName: String) =
        remote.profile(userName)


    suspend fun follow(userName: String, followRequest: FollowRequest) =
        remote.follow(userName, followRequest)

    suspend fun unFollow(userName: String) =
        remote.unFollow(userName)

    suspend fun getAllArticleOfPersonNew(
        author: String,
        pageNumber: Int
    ): MutableList<ArticleUser> {
        val result = remote.getAllArticleOfPersonNew(author, pageNumber)

        if (result is ResultCallBack.Success) {
            local.db.withTransaction {
                local.addUsers(result.data.articles.map {
                    it.mapToUserEntity()
                })
                local.addArticle(result.data.articles.map {
                    it.mapToEntity()
                })
                result.data.articles.forEach { article ->

                    local.insertTags(article.tagList.map { tag ->
                        TagModel(tag)
                    })

                    local.addArticleTag(article.tagList.map { tag ->
                        TagAndArticleEntity(tag, article.slug)
                    })
                }
            }

        }
        Log.d("UserRepository", "ss:${author} bib ${local.db.userDao().getArticlesByAuthor(author)} ")

        return local.db.userDao().getArticlesByAuthor(author)
    }

    suspend fun getFavoritedArticlesByUserName(author: String): MutableList<ArticleUser> {
        val result = remote.getFavoritedArticleByUserName(author)

        if (result is ResultCallBack.Success) {
            local.db.withTransaction {
                local.addUsers(result.data.articles.map {
                    it.mapToUserEntity()
                })
                local.addArticle(result.data.articles.map {
                    it.mapToEntity()
                })

                local.addUserAndFavoriteArticles(result.data.articles.map { article ->
                    UserAndHisFavoriteArticle(author, article.slug)
                })

                result.data.articles.forEach { article ->

                    local.insertTags(article.tagList.map { tag ->
                        TagModel(tag)
                    })

                    local.addArticleTag(article.tagList.map { tag ->
                        TagAndArticleEntity(tag, article.slug)
                    })
                }
            }

        }
        Log.d("UserRepository", "bb: ${local.db.userDao().getFavoriteArticlesByUser(author)} ")
        return local.db.userDao().getFavoriteArticlesByUser(author)
    }


}

//    val localDataSource = LocalDataSource()
//    val userLocalDataSource=UserLocalDataSource()

//
//init {
//
//
//}
//
//    fun getPostInProf(): MutableList<ArticleView> {
//        return localDataSource.getPostInProf()
//    }