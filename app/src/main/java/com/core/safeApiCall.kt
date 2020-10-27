package com.core

import com.core.util.Resource
import retrofit2.Response

suspend inline fun <T> safeApiCall(responseFunction: suspend () -> Response<T>): ResultCallBack<T> {

    try {
        val result = responseFunction.invoke()
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
        return ResultCallBack.Error(Exception("خطا در برقراری ارتباط"))
    }
}

suspend inline fun <T> safeApiCallResource(responseFunction: suspend () -> Response<T>): Resource<T> {

    try {
        val result = responseFunction.invoke()
        if (result.isSuccessful) {
            result.body()?.let {
                it.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(result.message())
        }
        return Resource.Error(result.message())
    } catch (e: Exception) {
        return Resource.Error("خطا در برقراری ارتباط")
    }
}
