@file:Suppress("DEPRECATION")

package com.core

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by moha on 9/14/2020.
 */
object Network {

    fun isNetworkConnected(): Boolean {

        return true
//        return try {
//            val ipAddr = InetAddress.getByName("www.google.com")
//            !ipAddr.equals("")
//        } catch (e: java.lang.Exception) {
//            false
//
    }
//    fun isConnected(context: Context?): Boolean {
//        val info: NetworkInfo? = context?.let { getNetworkInfo(it) }
//        return info != null && info.isConnected
//    }
//    fun getNetworkInfo(context: Context): NetworkInfo {
//        val cm =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return cm.activeNetworkInfo
//    }

    fun hasActiveInternetConnection(context: Context?): Boolean {
        if (isNetworkAvailable(context)) {
            try {
                val urlc: HttpURLConnection =
                    URL("http://www.google.com").openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Test")
                urlc.setRequestProperty("Connection", "close")
                urlc.connectTimeout = 1500
                urlc.connect()
                return urlc.responseCode === 200
            } catch (e: IOException) {
                Log.e("LOG_TAG", "Error checking internet connection", e)
            }
        } else {
            Log.d("LOG_TAG", "No network available!")
        }
        return false
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null
    }
}