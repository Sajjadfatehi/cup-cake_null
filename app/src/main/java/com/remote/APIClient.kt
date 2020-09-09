package com.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.config.MyApp
import com.config.Setting
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by moha on 9/8/2020.
 */
object APIClient {
    val responseStatus = MutableLiveData<Response>()
    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val proceed = chain.proceed(request = chain.request())
                responseStatus.postValue(proceed)
                return proceed
            }
        })
        .addNetworkInterceptor(FlipperOkhttpInterceptor(MyApp.networkFlipperPlugin))
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.5.69:3000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}