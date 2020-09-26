package com.user.data

import com.core.Network.isNetworkConnected
import com.core.ResultCallBack


/**
 * Created by moha on 9/12/2020.
 */

class UserRepo(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) {
    suspend fun login(loginReq: LoginReq): ResultCallBack<LoginRes> {
        return if (isNetworkConnected()) {
            userRemoteDataSource.login(loginReq)
        } else {
            ResultCallBack.Error(Exception("no net"))
        }
    }

    fun setTokenInShared(token: String?) {
        userLocalDataSource.saveToken(token)
    }

    fun setUserNameInShared(username: String?) {
        userLocalDataSource.saveUserName(username)

    }

    fun setEmailInShared(email: String?) {
        userLocalDataSource.saveEmailName(email)

    }

    suspend fun validUser(): ResultCallBack<LoginRes> {
        return if (isNetworkConnected()) {
            userRemoteDataSource.validUser()
        } else {
            ResultCallBack.Error(Exception("no net"))
        }
    }

}