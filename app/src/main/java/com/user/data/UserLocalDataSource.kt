package com.user.data

import android.content.SharedPreferences
import com.config.MyApp
import com.storage.data.PreferenceProperty.Companion.getPreferences
import com.storage.data.Settings

/**
 * Created by moha on 9/12/2020.m
 */
class UserLocalDataSource(val sherPref: SharedPreferences, val userDao: UserDao) {

    private val settings = Settings(MyApp.app.applicationContext.getPreferences())
    fun saveToken(token: String) {
        settings.token = token
    }

    fun getToken() = settings.token
}