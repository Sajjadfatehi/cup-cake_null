package com.core

sealed class ResultCallBack<out T> {

    data class Success<out T>(val data: T) : ResultCallBack<T>()
    data class Loading<T>(val string: String) : ResultCallBack<T>()
    data class Error<out T>(val exception: Throwable, val data: T? = null) : ResultCallBack<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error<*> -> "Error[exception=$exception, data=$data]"
            is Loading<T> -> "ResultCallBack<T>()"
        }
    }
}