package com.user.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.ResultCallBack
import com.user.data.UserRepository
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(val userRepository: UserRepository) : ViewModel() {

    var user = MutableLiveData<ResultCallBack<RegisterResponse>>()
    var registerResponse: RegisterResponse? = null
    var isRegisterSuccess = MutableLiveData<Boolean>()
    var password = MutableLiveData<String>()
    var username = MutableLiveData<String>()


    fun register(registerRequest: RegisterRequest) = viewModelScope.launch(Dispatchers.IO) {
        user.postValue(ResultCallBack.Loading(""))
        var response = userRepository.register(registerRequest)
        user.postValue(handleRegister(response))

    }


    init {
//        register(RegisterRequest(User(email = "asgharAghamamal@gmail.com",username = "asgharddBaby",password = "1234567890")))

    }

    //
//    fun handleRegister(response: Response<RegisterResponse>):Resource<RegisterResponse>{
//        if (response.isSuccessful){
//            isRegisterSuccess.postValue(true)
//            response.body()?.let { resultResponse->
//                if (registerResponse==null){
//                    registerResponse=resultResponse
//                }
//                return Resource.Success(registerResponse?:resultResponse)
//
//            }
//
//        }
//        isRegisterSuccess.postValue(false)
//
//        return Resource.Error(response.message())
//    }
    fun handleRegister(response: ResultCallBack<RegisterResponse>): ResultCallBack<RegisterResponse> {
        if (response is ResultCallBack.Success) {
            isRegisterSuccess.postValue(true)
            response.data.let { resultResponse ->
                if (registerResponse == null) {
                    registerResponse = resultResponse
                }
                userRepository.setTokenInShared(resultResponse.user.token)
                userRepository.setUserNameInShared(resultResponse.user.username)
                userRepository.setEmailInShared(resultResponse.user.email)
                return ResultCallBack.Success(registerResponse ?: resultResponse)

            }

        }
        isRegisterSuccess.postValue(false)

        return ResultCallBack.Error(Exception("some error happened"))
    }
}
