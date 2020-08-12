package com.config.sharedPreference

import android.content.Context
import android.content.SharedPreferences

/**
Created by Moha.Azizi on 12/08/2020 .
 */
class SharedPreference(private val context: Context?){
    val PREF_STATISTICS = "null"
    var shp: SharedPreferences? = null
    init {
        try {
            shp =
                context?.getSharedPreferences(PREF_STATISTICS, Context.MODE_PRIVATE)
        } catch (ex: Exception) {
        }
    }

    //-----------------------------------------
    fun PublicSharedPreferences(): PublicSharedPreferences? {
        return PublicSharedPreferences(shp)
    }
}