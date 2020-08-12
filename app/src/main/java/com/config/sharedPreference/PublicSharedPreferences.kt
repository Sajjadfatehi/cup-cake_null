package com.config.sharedPreference

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

/**
Created by Moha.Azizi on 12/08/2020 .
 */

class PublicSharedPreferences(){
    private val is_login = "Is_login"

    private var shp: SharedPreferences? = null
    private var shpe: Editor? = null

//    //-----------------------------------------------
//    fun publicSharedPreferences(shp: SharedPreferences) {
//        try {
//            this.shp = shp
//            shpe = shp.edit()
//        } catch (ex: Exception) {
//        }
//    }

    constructor(shp: SharedPreferences?) : this() {
        try {
            this.shp = shp
            shpe = shp?.edit()
        } catch (ex: Exception) {
        }
    }
    //-----------------------------------------------
    fun getToken(): String? {
        return shp!!.getString(is_login, "1")
    }

    fun setToken(value: String?) {
        try {
            if (shpe != null) {
                shpe!!.putString(is_login, value)
                shpe!!.commit()
            }
        } catch (ex: java.lang.Exception) {
        }
    }
}