package com.home.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.ResultCallBack
import com.user.data.UserRepo
import kotlinx.coroutines.launch

/**
 * Created by moha on 9/14/2020.
 */
class SplashViewModel(val userRepo: UserRepo) : ViewModel() {
    val isValid = MutableLiveData<Boolean>()
    lateinit var message: String


    fun validUser() {
        viewModelScope.launch {
            val response = userRepo.validUser()

            if (response is ResultCallBack.Success) {
                isValid.value = true
            } else if (response is ResultCallBack.Error) {
                isValid.value = false
                message = response.exception.message.toString()

            }
        }
    }


}