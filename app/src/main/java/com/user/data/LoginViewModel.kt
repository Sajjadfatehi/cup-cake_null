package com.user.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.ResultCallBack
import kotlinx.coroutines.launch

/**
 * Created by moha on 9/12/2020.
 */
class LoginViewModel(val repo: UserRepo) : ViewModel() {

    val result = MutableLiveData<Boolean>()
    var password = MutableLiveData<String>()
    var username = MutableLiveData<String>()
    var isLogin = MutableLiveData<Boolean>()
    lateinit var message: String


    fun login() {
        var loginReq = LoginReq(
            LoginUserModel(
                email = username.value.orEmpty(),
                password = password.value.orEmpty()
            )
        )


        result.postValue(true)

        viewModelScope.launch {

            val response = repo.login(loginReq)

            if (response is ResultCallBack.Error) {
                message = response.exception.message.toString()
                isLogin.value = false
            } else if (response is ResultCallBack.Success) {
                isLogin.value = true
                repo.setTokenInShared(response.data.user.token)
                repo.setUserNameInShared(response.data.user.username)
                repo.setEmailInShared(response.data.user.email)
            }
            result.postValue(false)
        }
    }

}