package com.auth

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anull.R
import com.part.myapplication.models.AuthRequest
import com.part.myapplication.models.AuthResponse
import com.part.myapplication.models.User
import com.remote.APIClient.retrofit
import com.remote.AuthApi
import kotlinx.coroutines.*

/**
 * Created by moha on 9/9/2020.
 */
data class LoginViewModel(val application: Application) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
    }

    //region Vars
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val authApi = retrofit.create(AuthApi::class.java)
    //endregion

         fun login(){
        uiScope.launch {
            val result = safeApiCall {
                authApi.login(
                    authRequest = AuthRequest(
                        User(
                            email = "test@part.ir",
                            password = "11111111"
                        )
                    )
                )
            }

            result?.let {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        application.applicationContext,
                        it.user.email,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } ?: run {

            }

        }

    }

    suspend inline fun <T> safeApiCall(responseFunction: suspend () -> T): T? {
        return try {
            responseFunction.invoke()//Or responseFunction()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}