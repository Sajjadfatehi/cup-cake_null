package com.user.data.reomtedatasource

import android.util.Log
import com.article.data.ArticleModel
import com.article.data.modelfromservice.ArticleResponse
import com.core.ResultCallBack
import com.core.RetrofitUtil
import com.user.data.api.UserApi
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.Profile
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.RegisterResponse

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


    suspend fun register(registerRequest: RegisterRequest): ResultCallBack<RegisterResponse> {
        try {
            val result = retrofit.register(registerRequest)
            if (result.isSuccessful) {
                result.body()?.let {
                    return ResultCallBack.Success(it)
                }
                return ResultCallBack.Error(java.lang.Exception("server data failed"))
            }
            return ResultCallBack.Error(java.lang.Exception(result.code().toString()))
        } catch (e: Exception) {
            return ResultCallBack.Error(Exception("bad request"))
        }
    }


    suspend fun favoriteArticle(slug: String): ResultCallBack<ArticleResponse> {
        val result = retrofit.favoriteArticle(slug)

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

    suspend fun unFavoriteArticle(slug: String): ResultCallBack<ArticleResponse> {
        val result = retrofit.unFavoriteArticle(slug)

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