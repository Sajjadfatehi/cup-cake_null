package com.article.data.remotedatasource

import com.article.data.ArticleModel
import com.article.data.api.ArticleApi
import com.article.data.modelfromservice.*
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.core.safeApiCall
import com.core.safeApiCallResource
import com.core.util.Resource
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

    suspend fun createComment(
        slug: String,
        commentRequest: CommentRequest
    ): Resource<CommentResponse> {
        return safeApiCallResource { articleApi.createComment(slug, commentRequest) }
    }

    suspend fun getAllCommentsForArticle(slug: String): Resource<AllCommentsResponse> {
        return safeApiCallResource { articleApi.getAllCommentsForArticle(slug) }
    }


}