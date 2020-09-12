package com.user.data

import android.content.SharedPreferences
import com.config.Setting


/**
 * Created by moha on 9/12/2020.m
 */
class UserLocalDataSource(val sherPref: SharedPreferences, val userDao: UserDao) {
    fun setToken(token: String) {
        sherPref.edit()
            .putString(TOKEN, token)
            .apply()
    }

    companion object {
        const val TOKEN = "token"
    }
}