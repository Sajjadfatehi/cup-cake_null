package com.user.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.ResultCallBack
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

/**
 * Created by moha on 9/12/2020.
 */
class LoginViewModel(val repo: UserRepo) : ViewModel() {
    fun login(loginReq: LoginReq, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val response = repo.login(loginReq)
            if (response is ResultCallBack.Error) {
                callback.invoke(false, response.exception.message.toString())
            } else if (response is ResultCallBack.Success) {
                callback.invoke(true, null)
                repo.setTokenInShared(response.data.user.token)
            }
        }
    }
}