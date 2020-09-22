package com.article.data.remotedatasource

import android.util.Log
import com.article.data.ArticleModel
import com.article.data.api.ArticleApi
import com.article.data.modelfromservice.ArticleResponse
import com.article.data.modelfromservice.CreateArticleModel
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.core.util.Resource
import com.user.data.modelfromservice.EditArticleRequest
import java.lang.Exception

class ArticleRemoteDataSource {
    val articleApi = RetrofitUtil.getInstance().create(ArticleApi::class.java)


    suspend fun updateArticle(slug: String, editArticleRequest: EditArticleRequest): Resource<ArticleResponse> {
        val result = articleApi.updateArticle(slug,editArticleRequest)
        try {
            if (result.isSuccessful) {
                result.body()?.let {
                    it?.let {
                        return Resource.Success(it)
                    }
                }
                return Resource.Error(result.message())
            }
            return Resource.Error(result.code().toString())
        } catch (e: Exception) {
            Log.i("Exception", "getArticleWithTag: Exception user remote data source ")
            return Resource.Error(result.message())
        }
    }

    suspend fun createArticle(createArticleModel: CreateArticleModel):Resource<ArticleResponse>{
       val result= articleApi.createArticle(createArticleModel)
        try {
            if (result.isSuccessful) {
                result.body()?.let {
                    it?.let {
                        return Resource.Success(it)
                    }
                }
                return Resource.Error(result.message())
            }
            return Resource.Error(result.code().toString())
        } catch (e: Exception) {
            Log.i("Exception", "getArticleWithTag: Exception user remote data source ")
            return Resource.Error(result.message())
        }
    }
}