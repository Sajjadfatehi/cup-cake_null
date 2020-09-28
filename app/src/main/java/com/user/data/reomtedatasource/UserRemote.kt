package com.user.data.reomtedatasource

import android.util.Log
import com.article.data.ArticleModel
import com.article.data.modelfromservice.ArticleResponse
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.core.safeApiCall
import com.user.data.api.UserApi
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.Profile
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.RegisterResponse

class UserRemote {
    val retrofit = RetrofitUtil.getInstance().create(UserApi::class.java)


    suspend fun getAllArticleOfPersonNew(author: String): ResultCallBack<ArticleModel> {
        return safeApiCall { retrofit.getAllArticleOfPerson(author) }

    }

    suspend fun getFavoritedArticleByUserName(author: String): ResultCallBack<ArticleModel> {
        return safeApiCall { retrofit.favoriteArticleByUserName(author) }

    }

    suspend fun register(registerRequest: RegisterRequest): ResultCallBack<RegisterResponse> {
        return safeApiCall { retrofit.register(registerRequest) }
    }

    suspend fun favoriteArticle(slug: String): ResultCallBack<ArticleResponse> {
        return safeApiCall { retrofit.favoriteArticle(slug) }
    }

    suspend fun unFavoriteArticle(slug: String): ResultCallBack<ArticleResponse> {
        return safeApiCall { retrofit.unFavoriteArticle(slug) }
    }

    suspend fun deleteArticle(slug: String): ResultCallBack<Unit> {
        val result = retrofit.deleteArticle(slug)
        try {
            if (result.isSuccessful) {

                return ResultCallBack.Success(Unit)

                //                return ResultCallBack.Error(Exception("server data failed"))
            }
            return ResultCallBack.Error(Exception(result.code().toString()))
        } catch (e: Exception) {
            Log.i("Exception", "getArticleWithTag: Exception user remote data source ")
            return ResultCallBack.Error(Exception("bad request"))
        }
    }

    suspend fun profile(userName: String): ResultCallBack<Profile> {
        return safeApiCall { retrofit.profile(userName) }
    }

    suspend fun follow(userName: String, followRequest: FollowRequest): ResultCallBack<Profile> {
        return safeApiCall { retrofit.follow(userName, followRequest) }
    }

    suspend fun unFollow(userName: String): ResultCallBack<Profile> {
        return safeApiCall { retrofit.unFollow(userName) }
    }
}