package com.core

import androidx.lifecycle.MutableLiveData
import com.config.MyApp
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.storage.data.PreferenceProperty.Companion.getPreferences
import com.storage.data.Settings
import com.user.data.UserLocalDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by moha on 9/12/2020.
 */
object RetrofitUtil {
    val responseStatus = MutableLiveData<Response>()
    val loginLocalDataSource = UserLocalDataSource(sherPref = MyApp.app.applicationContext.getPreferences(),userDao = null)

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

    private fun getOkHttpClient() = OkHttpClient.Builder().addNetworkInterceptor(object :
        Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val proceed = chain.proceed(request = chain.request())
            responseStatus.postValue(proceed)
            return proceed
        }
    })
        .addInterceptor { chain ->
            var newBuilder = chain.request().newBuilder()
            loginLocalDataSource.getToken()?.let { token ->
                newBuilder = newBuilder.addHeader("token", "Token $token")
            }
            val request = newBuilder.build()
            chain.proceed(request)
        }
        .addNetworkInterceptor(FlipperOkhttpInterceptor(MyApp.networkFlipperPlugin))
        .build()

}