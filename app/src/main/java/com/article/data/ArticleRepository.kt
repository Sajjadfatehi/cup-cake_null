package com.article.data

import androidx.room.withTransaction
import com.article.data.localdatasource.ArticleLocalDataSource
import com.article.data.modelfromservice.*
import com.article.data.remotedatasource.ArticleRemoteDataSource
import com.config.MyApp
import com.core.Network
import com.core.ResultCallBack
import com.core.RoomDataBase
import com.core.util.Resource
import com.user.data.modelfromservice.EditArticleRequest

class ArticleRepository(
    val articleLocalDataSource: ArticleLocalDataSource,
    val articleRemoteDataSource: ArticleRemoteDataSource
) {

    val localDataSource = HomeLocalDataSource()
    val remoteDataSource = HomeRemoteDataSource()

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
                TagAndArticleEntity(text, it.slug)
            })

        }
        return localDataSource.getArticleWithTag(text)
    }

    suspend fun getArticleByTag(tag: String): ResultCallBack<List<ArticleUser>> {
        if (Network.hasActiveInternetConnection(MyApp.app.applicationContext)) {
            val result = articleRemoteDataSource.gteArticleByTagNew(tag)

            if (result is ResultCallBack.Success) {

                articleLocalDataSource.db.tagDao().deleteTagAndArticleByTag(tag)
                articleLocalDataSource.addUsers(result.data.articles.map {
                    it.mapToUserEntity()
                })
                articleLocalDataSource.addArticle(result.data.articles.map {
                    it.mapToEntity()
                })

                result.data.articles.forEach { article ->
                    articleLocalDataSource.insertTags(article.tagList.map { tag ->
                        TagModel(tag)
                    })
                    articleLocalDataSource.addArticleTag(article.tagList.map { tag ->
                        TagAndArticleEntity(tag, article.slug)
                    })
                }
                return ResultCallBack.Success(result.data.articles.map {
                    it.toArticleView()
                })
            }
            return ResultCallBack.Error(Throwable("read from api in not successful"))

        }

        //read from database
        return ResultCallBack.Success(articleLocalDataSource.getArticleByTag(tag))

    }

    suspend fun createArticle(createArticleModel: CreateArticleModel): ResultCallBack<ArticleResponse> {
        if (Network.hasActiveInternetConnection(MyApp.app.applicationContext)) {
            val result = articleRemoteDataSource.createArticle(createArticleModel)
            if (result is ResultCallBack.Success) {

                articleLocalDataSource.db.withTransaction {
                    result.data.article.mapToUserEntity().let { userEntity ->
                        articleLocalDataSource.addUsers(listOf(userEntity))
                    }
                    result.data.article.mapToEntity().let { article ->
                        articleLocalDataSource.insertArticle(listOf(article))
                    }
                    result.data.article.tagList.map { tag ->
                        TagModel(tag)
                    }.let { articleLocalDataSource.insertTags(it) }

                    result.data.article.tagList.map { tag ->
                        TagAndArticleEntity(tag, result.data.article.slug)
                    }.let { articleLocalDataSource.addArticleTag(it) }
                }

            }
            return result
        } else {
            return ResultCallBack.Error(Exception("از اتصال خود به اینترنت اطمینان حاصل کنید "))
        }

    }

    suspend fun updateArticle(
        slug: String,
        editArticleRequest: EditArticleRequest
    ): ResultCallBack<ArticleResponse> {
        return if (Network.hasActiveInternetConnection(MyApp.app)) {
            val result = articleRemoteDataSource.updateArticle(slug, editArticleRequest)

            if (result is ResultCallBack.Success) {
                articleLocalDataSource.db.withTransaction {
                    result.data.article.mapToEntity()
                        .let { articleLocalDataSource.updateArticle(it) }
                }
            }
            result
        } else {
            ResultCallBack.Error(Exception("از اتصال خود به اینترنت اطمینان حاصل کنید "))
        }

    }

    suspend fun favoriteArticle(
        slug: String,
        ownUserName: String
    ): ResultCallBack<ArticleResponse> {

        if (Network.hasActiveInternetConnection(MyApp.app)) {
            val result = articleRemoteDataSource.favoriteArticle(slug)

            if (result is ResultCallBack.Success) {
                articleLocalDataSource.db.withTransaction {

                    articleLocalDataSource.addArticle(listOf(result.data.article.mapToEntity()))
                    articleLocalDataSource.addUserAndFavoriteArticles(
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

        } else {
            return ResultCallBack.Error(Exception("اینترنت متصل نیست"))
        }
    }

    suspend fun unFavoriteArticle(
        slug: String,
        ownUserName: String
    ): ResultCallBack<ArticleResponse> {

        if (Network.hasActiveInternetConnection(MyApp.app)) {

            val result = articleRemoteDataSource.unFavoriteArticle(slug)

            if (result is ResultCallBack.Success) {
                articleLocalDataSource.db.withTransaction {

                    articleLocalDataSource.addArticle(listOf(result.data.article.mapToEntity()))
                    articleLocalDataSource.deleteUserAndFavoriteArticles(
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

    suspend fun createComment(
        slug: String,
        commentRequest: CommentRequest
    ): Resource<CommentResponse> {
        return if (Network.hasActiveInternetConnection(MyApp.app)) {
            val result = articleRemoteDataSource.createComment(slug, commentRequest)
            result
        } else {
            Resource.Error("connect to server failed")
        }


    }


    fun getUserNameFromShare() = articleLocalDataSource.getUserNameFromShare()

    fun getTitleFromShare() = articleLocalDataSource.getTitleFromShare()

    fun getBodyFromShare() = articleLocalDataSource.getBodyFromShare()

    fun saveTitleInSharePref(title: String) {
        articleLocalDataSource.saveTitle(title)
    }

    fun saveBodyInSharePref(body: String) {
        articleLocalDataSource.saveBody(body)
    }
}
