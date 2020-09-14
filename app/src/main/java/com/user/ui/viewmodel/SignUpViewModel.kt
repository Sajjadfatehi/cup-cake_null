package com.user.ui.viewmodel


import android.hardware.usb.UsbRequest
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import com.user.data.UserRepository
import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.RegisterResponse
import com.user.data.modelfromservice.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel(val userRepository: UserRepository) : ViewModel(){

    var user=MutableLiveData<Resource<RegisterResponse>>()
    var registerResponse:RegisterResponse?=null
    var isRegisterSuccess=MutableLiveData<Boolean>()


    fun register(registerRequest:RegisterRequest)=viewModelScope.launch(Dispatchers.IO){
        user.postValue(Resource.Loading())
        var response=userRepository.register(registerRequest)
        user.postValue(handleRegister(response))

        Log.d("lashii", "register tt: ${user.value?.data.toString()} ")
    }


    init {
//        register(RegisterRequest(User(email = "asgharAghamamal@gmail.com",username = "asgharddBaby",password = "1234567890")))

    }

    fun handleRegister(response: Response<RegisterResponse>):Resource<RegisterResponse>{
        if (response.isSuccessful){
            isRegisterSuccess.postValue(true)
            Log.d("lashii", "reg : ${response.body()?.user?.token} + ${response.body()?.user?.email}")
            response.body()?.let { resultResponse->
                if (registerResponse==null){
                    registerResponse=resultResponse
                }
                return Resource.Success(registerResponse?:resultResponse)

            }

        }
        isRegisterSuccess.postValue(false)

        return Resource.Error(response.message())
    }
}
