package com.article.data.remotedatasource

import com.article.data.ArticleModel
import com.article.data.api.ArticleApi
import com.article.data.modelfromservice.ArticleResponse
import com.article.data.modelfromservice.CreateArticleModel
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.core.safeApiCall
import com.user.data.modelfromservice.EditArticleRequest

class ArticleRemoteDataSource {
    val articleApi = RetrofitUtil.getInstance().create(ArticleApi::class.java)


    suspend fun updateArticle(
        slug: String,
        editArticleRequest: EditArticleRequest
    ): ResultCallBack<ArticleResponse> {
        return safeApiCall { articleApi.updateArticle(slug, editArticleRequest) }
    }

    suspend fun createArticle(createArticleModel: CreateArticleModel): ResultCallBack<ArticleResponse> {
        return safeApiCall { articleApi.createArticle(createArticleModel) }

    }

    suspend fun favoriteArticle(slug: String): ResultCallBack<ArticleResponse> {
        return safeApiCall { articleApi.favoriteArticle(slug) }
    }

    suspend fun unFavoriteArticle(slug: String): ResultCallBack<ArticleResponse> {
        return safeApiCall { articleApi.unFavoriteArticle(slug) }
    }


    suspend fun gteArticleByTagNew(tag: String): ResultCallBack<ArticleModel> {
        return safeApiCall { articleApi.getArticlesByTagNew(tag) }

    }

}