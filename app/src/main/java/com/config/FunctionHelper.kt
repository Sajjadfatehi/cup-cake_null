package com.config

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.config.sharedPreference.PublicSharedPreferences

/**
Created by Moha.Azizi on 12/08/2020 .
 */
class FunctionHelper {

    //    //-------------------------------------
    //    public settingSharedPreferences getSettingSharedPreferences(Context c) {
    //        SharedPreference preference = new SharedPreference(c);
    //        return preference.SettingSharedPreferences();
    //    }
    //------------------------------------------------------------
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
    //------------------------------------------------------------
//    fun getPublicSharedPreferences(context: Context?): PublicSharedPreferences? {
//        val preference = SharedPreference(context)
//        return preference.PublicSharedPreferences()
//    }

}