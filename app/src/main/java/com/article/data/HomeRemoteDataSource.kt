package com.article.data

import android.util.Log
import com.core.ResultCallBack
import com.core.RetrofitUtil
import java.lang.Exception

/**
 * Created by moha on 9/15/2020.
 */
class HomeRemoteDataSource() {

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
}