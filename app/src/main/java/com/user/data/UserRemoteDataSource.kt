package com.user.data

import android.util.Log
import com.core.ResultCallBack
import com.core.RetrofitUtil

/**
 * Created by moha on 9/12/2020.
 */
class UserRemoteDataSource() {
    val userApi = RetrofitUtil.getInstance().create(UserApi::class.java)

    suspend fun login(loginReq: LoginReq): ResultCallBack<LoginRes> {
        try {
            val result = userApi.login(loginReq)
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

    suspend fun validUser(): ResultCallBack<LoginRes> {
        try {
            val result = userApi.validUser()

            if (result.isSuccessful) {
                result.body()?.let {
                    return ResultCallBack.Success(it)
                }

                return ResultCallBack.Error(Exception("server data failed"))
            }
            return ResultCallBack.Error(Exception(result.code().toString()))
        } catch (e: Exception) {
            Log.i("Exception", "Exception user remote data source")
            return ResultCallBack.Error(Exception("bad request"))
        }
    }

}