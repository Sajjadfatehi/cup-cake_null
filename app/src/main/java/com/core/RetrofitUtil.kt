package com.core

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by moha on 9/12/2020.
 */
object RetrofitUtil {

    private var retrofit: Retrofit? = null
    private const val baseURL = "http://192.168.5.69:3000/api/"
    private val networkFlipperPlugin = NetworkFlipperPlugin()

    fun getInstance(): Retrofit {
        return retrofit ?: getRetrofit().also { retrofit = it }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient() = OkHttpClient.Builder().addNetworkInterceptor(
        FlipperOkhttpInterceptor(networkFlipperPlugin)
    ).build()

}