package com.user.data.reomtedatasource

import android.util.Log
import com.article.data.ArticleModel
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.user.data.api.UserApi
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.Profile
import com.user.data.modelfromservice.RegisterRequest

class UserRemote {
    val retrofit = RetrofitUtil.getInstance().create(UserApi::class.java)


    suspend fun getAllArticleOfPerson(author: String, pageNumber: Int) =
        retrofit.getAllArticleOfPerson(author, pageNumber)


    suspend fun getAllArticleOfPersonNew(
        author: String,
        pageNumber: Int
    ): ResultCallBack<ArticleModel> {
        val result = retrofit.getAllArticleOfPerson(author, pageNumber)
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

    suspend fun getFavoritedArticleByUserName(author: String): ResultCallBack<ArticleModel> {
        val result = retrofit.favoriteArticleByUserName(author)
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


    suspend fun register(registerRequest: RegisterRequest) =
        retrofit.register(registerRequest)

    suspend fun favoriteArticle(slug: String) =
        retrofit.favoriteArticle(slug)

    suspend fun unFavoriteArticle(slug: String) =
        retrofit.unFavoriteArticle(slug)


    suspend fun favoritedArticleByUserName(favoritedUserName: String) =
        retrofit.favoriteArticleByUserName(favoritedUserName)

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
        val result = retrofit.profile(userName)
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

    suspend fun follow(userName: String, followRequest: FollowRequest): ResultCallBack<Profile> {
        val result = retrofit.follow(userName, followRequest)
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


    suspend fun unFollow(userName: String): ResultCallBack<Profile> {
        val result = retrofit.unFollow(userName)
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
}