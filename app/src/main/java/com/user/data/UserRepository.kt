package com.user.data


import androidx.room.withTransaction
import com.article.data.ArticleUser
import com.article.data.TagAndArticleEntity
import com.article.data.TagModel
import com.article.data.modelfromservice.ArticleResponse
import com.article.data.modelfromservice.UserAndHisFavoriteArticle
import com.config.MyApp
import com.core.Network
import com.core.ResultCallBack
import com.user.data.localdatasource.UserLocalDataSource
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.RegisterResponse
import com.user.data.reomtedatasource.UserRemote


class UserRepository(val local: UserLocalDataSource, val remote: UserRemote) {

    suspend fun register(registerRequest: RegisterRequest): ResultCallBack<RegisterResponse> {
        return if (Network.isNetworkConnected()) {
            remote.register(registerRequest)
        } else {
            ResultCallBack.Error(Exception("no net"))
        }

    }

    suspend fun favoriteArticle(
        slug: String,
        ownUserName: String
    ): ResultCallBack<ArticleResponse> {

        if (Network.hasActiveInternetConnection(MyApp.app)) {
            val result = remote.favoriteArticle(slug)

            if (result is ResultCallBack.Success) {
                local.db.withTransaction {

                    local.addArticle(listOf(result.data.article.mapToEntity()))
                    local.addUserAndFavoriteArticles(
                        listOf(UserAndHisFavoriteArticle(ownUserName, slug))
                    )

                }

            }
            return result

        } else {
            return ResultCallBack.Error(Exception("اینترنت متصل نیست"))
        }
    }

    suspend fun unFavoriteArticle(
        slug: String,
        ownUserName: String
    ): ResultCallBack<ArticleResponse> {

        if (Network.hasActiveInternetConnection(MyApp.app)) {

            val result = remote.unFavoriteArticle(slug)

            if (result is ResultCallBack.Success) {
                local.db.withTransaction {

                    local.addArticle(listOf(result.data.article.mapToEntity()))
                    local.deleteUserAndFavoriteArticles(
                        listOf(
                            UserAndHisFavoriteArticle(
                                ownUserName,
                                slug
                            )
                        )
                    )

                }

            }
            return result
        } else return ResultCallBack.Error(Exception("اینترنت متصل نیست"))
    }

    suspend fun deleteArticle(slug: String): ResultCallBack<Unit> {
        return if (Network.hasActiveInternetConnection(MyApp.app)) {
            val result = remote.deleteArticle(slug)
            if (result is ResultCallBack.Success) {
                local.deleteArticle(slug)
            }
            result
        } else {
            ResultCallBack.Error(Exception("لطفا از اتصال خود به اینترنت اصمینان حاصل فرمایید"))
        }

    }

    suspend fun profile(userName: String): ResultCallBack<UserEntity> {
        if (Network.hasActiveInternetConnection(MyApp.app)) {

            val result = remote.profile(userName)
            if (result is ResultCallBack.Success) {

                local.db.withTransaction {

                    local.addUsers(listOf(result.data.profile.mapYoUserEntity()))

                }
                return ResultCallBack.Success(local.getUser(userName))

            }

            return ResultCallBack.Error(Exception("follow failed"))

        } else {
            return ResultCallBack.Success(local.getUser(userName))
        }


    }

    suspend fun profileLocal(userName: String): ResultCallBack<UserEntity> {
        return ResultCallBack.Success(local.getUser(userName))
    }

    suspend fun follow(userName: String, followRequest: FollowRequest): ResultCallBack<UserEntity> {
        if (Network.hasActiveInternetConnection(MyApp.app)) {
            val result = remote.follow(userName, followRequest)


            if (result is ResultCallBack.Success) {
                local.db.withTransaction {

                    local.addUsers(listOf(result.data.profile.mapYoUserEntity()))
                }
                return ResultCallBack.Success(local.getUser(userName))

            }
            return ResultCallBack.Error(Exception("follow failed"))

        } else {
            return ResultCallBack.Error(Exception("اینترنت متصل نیست"))
        }
    }

    suspend fun unFollow(userName: String): ResultCallBack<UserEntity> {

        if (Network.hasActiveInternetConnection(MyApp.app)) {
            val result = remote.unFollow(userName)

            if (result is ResultCallBack.Success) {
                local.db.withTransaction {

                    local.addUsers(listOf(result.data.profile.mapYoUserEntity()))
                }
                return ResultCallBack.Success(local.getUser(userName))

            }

            return ResultCallBack.Error(Exception("follow failed"))

        } else {
            return ResultCallBack.Error(Exception("اینترنت متصل نیست"))
        }

    }

    suspend fun getAllArticleOfPersonNew(author: String): MutableList<ArticleUser> {
        if (Network.hasActiveInternetConnection(MyApp.app.applicationContext)) {

            val result = remote.getAllArticleOfPersonNew(author)
            if (result is ResultCallBack.Success) {
                local.db.withTransaction {
                    local.db.articleDao().deleteArticleByUserName(author)
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
        }

        return local.db.userDao().getArticlesByAuthor(author)
    }

    suspend fun getAllArticleOfPersonNewLocal(author: String): MutableList<ArticleUser> {
        return local.db.userDao().getArticlesByAuthor(author)
    }

    suspend fun getFavoritedArticlesByUserName(author: String): MutableList<ArticleUser> {
        if (Network.hasActiveInternetConnection(MyApp.app.applicationContext)) {

            val result = remote.getFavoritedArticleByUserName(author)
            if (result is ResultCallBack.Success) {
                local.db.withTransaction {
                    local.db.articleDao().deleteFavoriteArticleOfUser(author)
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
        }

        return local.db.userDao().getFavoriteArticlesByUser(author)
    }

    suspend fun getFavoritedArticlesByUserNameLocal(author: String): MutableList<ArticleUser> {
        return local.db.userDao().getFavoriteArticlesByUser(author)
    }

    fun setTokenInShared(token: String?) {
        local.saveToken(token)
    }

    fun setUserNameInShared(username: String?) {
        local.saveUserName(username)

    }

    fun setEmailInShared(email: String?) {
        local.saveEmail(email)

    }

    fun getUserNameFromShare(): String {
        return local.getUserName().toString()
    }

    fun getEmailFromShare(): String {
        return local.getEmail().toString()
    }

    fun clearDb() = local.clearDb()

    fun clearSharePref() = local.clearSharePref()

}

