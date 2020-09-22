package com.article.data

import androidx.room.withTransaction
import com.article.data.api.ArticleApi
import com.article.data.localdatasource.ArticleLocalDataSource
import com.article.data.modelfromservice.ArticleResponse
import com.article.data.modelfromservice.CreateArticleModel
import com.article.data.remotedatasource.ArticleRemoteDataSource
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.core.RoomDataBase
import com.core.db.AppDataBase
import com.core.util.Resource
import com.user.data.modelfromservice.EditArticleRequest

class ArticleRepository(
    val articleLocalDataSource: ArticleLocalDataSource,
    val articleRemoteDataSource: ArticleRemoteDataSource
) {

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
                TagAndArticleEntity(text, it.slug)
            })

        }
        return localDataSource.getArticleWithTag(text)
    }

    suspend fun getArticleByTag(tag: String, pageNumber: Int) =
        retrofit.getArticlesByTag(tag, pageNumber)

    suspend fun createArticle(createArticleModel: CreateArticleModel): Resource<ArticleResponse> {
        val result = articleRemoteDataSource.createArticle(createArticleModel)
        if (result is Resource.Success) {

            articleLocalDataSource.db.withTransaction {
                result.data?.article?.mapToEntity()?.let { article ->
                    articleLocalDataSource.updateArticle(article)
                }
                result.data?.article?.mapToUserEntity()?.let { userEntity ->
                    articleLocalDataSource.addUsers(listOf(userEntity) )
                }

                result.data?.article?.tagList?.map { tag->
                    TagModel(tag)
                }?.let { articleLocalDataSource.insertTags(it) }

                result.data?.article?.tagList?.map { tag->
                    TagAndArticleEntity(tag,result.data.article.slug)
                }?.let { articleLocalDataSource.addArticleTag(it) }
            }

        }
        return result
    }


    suspend fun updateArticle(
        slug: String,
        editArticleRequest: EditArticleRequest
    ): Resource<ArticleResponse> {
        val result = articleRemoteDataSource.updateArticle(slug, editArticleRequest)



        if (result is Resource.Success) {
            articleLocalDataSource.db.withTransaction {
                result.data?.article?.mapToEntity()
                    ?.let { articleLocalDataSource.updateArticle(it) }

            }


        }
        return result
    }

}
