package com.core

suspend inline fun <T> safeApiCall(responseFunction: suspend () -> T): ResultCallBack<T> {
    return try {
        ResultCallBack.Success(responseFunction.invoke())
    } catch (e: Exception) {
        ResultCallBack.Error(e, null)
    }
}