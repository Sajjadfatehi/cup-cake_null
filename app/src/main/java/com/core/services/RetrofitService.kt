package com.core.services

import androidx.lifecycle.MutableLiveData
import com.core.util.Constants.Companion.BASE_URL

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val responseStatus = MutableLiveData<Response>()
    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmNjA5YjY2NWQwZDdjNWIwZWYwZTYyZSIsInVzZXJuYW1lIjoiNDQ0NDQ0NDQiLCJleHAiOjE2MDU0Mzk4OTIsImlhdCI6MTYwMDI1MjI5Mn0.ZpuOr9BmOHj8TMI0WdNgi1KgbI7-n_QlQCq7vXFtN3A"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val proceed = chain.proceed(request = chain.request())
                responseStatus.postValue(proceed)
                return proceed
            }
        })
        .addNetworkInterceptor(logging)

        .addInterceptor { chain ->
            var newBuilder = chain.request().newBuilder()
            token.let { token ->
                newBuilder = newBuilder.addHeader("Authorization", "Token $token")
            }
            val request = newBuilder.build()
            chain.proceed(request)
        }
        //.addNetworkInterceptor(FlipperOkhttpInterceptor(MyApp.networkFlipperPlugin))
        .build()

    val retrofit = Retrofit.Builder()

        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}