package com.core.services

import androidx.lifecycle.MutableLiveData
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val responseStatus = MutableLiveData<Response>()

    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val proceed = chain.proceed(request = chain.request())
                responseStatus.postValue(proceed)
                return proceed
            }
        })

//        .addInterceptor { chain ->
//            var newBuilder = chain.request().newBuilder()
//            loginLocalDataSource.getToken()?.let { token ->
//                newBuilder = newBuilder.addHeader("token", "Token $token")
//            }
//            val request = newBuilder.build()
//            chain.proceed(request)
//        }
        //.addNetworkInterceptor(FlipperOkhttpInterceptor(MyApp.networkFlipperPlugin))
        .build()

    val retrofit = Retrofit.Builder()
//        .baseUrl("http://192.168.5.69:3000/api/")
        .baseUrl("http://192.168.5.69:3000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}