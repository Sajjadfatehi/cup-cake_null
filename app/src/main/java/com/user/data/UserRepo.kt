package com.user.data

import com.core.ResultCallBack

/**
 * Created by moha on 9/12/2020.
 */

class UserRepo(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) {
    suspend fun login(loginReq: LoginReq): ResultCallBack<LoginRes> {
        return if (checkNet()) {
            userRemoteDataSource.login(loginReq)
        } else {
            ResultCallBack.Error(Exception("no net"))
        }
    }

    private fun checkNet(): Boolean {
        return true
    }
//    fun setTokenInShared(token : String){
//        userLocalDataSource.setToken(token)
//    }

}