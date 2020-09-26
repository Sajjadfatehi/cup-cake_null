package com.article.data

import android.util.Log
import com.article.data.api.ArticleApi
import com.article.data.modelfromservice.CreateArticleModel
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.user.data.modelfromservice.EditArticleRequest

/**
 * Created by moha on 9/15/2020.
 */
class HomeRemoteDataSource {

    val articleApi = RetrofitUtil.getInstance().create(ArticleApi::class.java)

    suspend fun getAllTags(): ResultCallBack<TagResponse> {
        try {
            val result = articleApi.getAllTags()

            if (result.isSuccessful) {
                result.body()?.let {
                    return ResultCallBack.Success(it)

                }
                return ResultCallBack.Error(Exception("server data failed"))
            }
            return ResultCallBack.Error(Exception(result.code().toString()))
        } catch (e: Exception) {
            Log.i("Exception", "getAllTags: Exception user remote data source ")
            return ResultCallBack.Error(Exception("bad request"))
        }
    }

    suspend fun getArticleWithTag(text: String): ResultCallBack<ArticleModel> {
        val result = articleApi.getArticleWithTag(text)

        try {
            if (result.isSuccessful) {
                result.body()?.let {
                    it.let {
                        return ResultCallBack.Success(it)
                    }
                }
                return ResultCallBack.Error(Exception("server data failed"))
            }
            return ResultCallBack.Error(Exception(result.code().toString()))
        } catch (e: Exception) {
            Log.i("Exception", "getArticleWithTag: Exception user remote data source ")
            return ResultCallBack.Error(Exception("bad request"))
        }
    }

    suspend fun getArticleByTag(tag: String, pageNumber: Int) =
        articleApi.getArticlesByTag(tag, pageNumber)

    suspend fun createArticle(createArticleModel: CreateArticleModel) =
        articleApi.createArticle(createArticleModel = createArticleModel)


    suspend fun updateArticle(slug: String, editArticleRequest: EditArticleRequest) =
        articleApi.updateArticle(slug, editArticleRequest)
}