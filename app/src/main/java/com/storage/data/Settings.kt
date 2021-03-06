package com.storage.data

import android.content.SharedPreferences
import com.part.myapplication.storage.types.IntPreference
import com.storage.type.BooleanPreference
import com.storage.type.StringPreference

class Settings(sharedPreferences: SharedPreferences) {

    var appVersion: Int by IntPreference(
        sharedPreferences,
        APP_VERSION
    )
    var appVersionName: String? by StringPreference(
        sharedPreferences,
        APP_VERSION_NAME
    )
    var isUpToDate: Boolean by BooleanPreference(
        sharedPreferences,
        IS_UP_TO_DATE
    )
    var token: String? by StringPreference(
        sharedPreferences,
        TOKEN
    )

    var username: String? by StringPreference(
        sharedPreferences,
        USERNAME
    )
    var email: String? by StringPreference(
        sharedPreferences,
        EMAIL
    )
    var title: String? by StringPreference(
        sharedPreferences,
        TITLE
    )
    var body: String? by StringPreference(
        sharedPreferences,
        BODY
    )

    companion object Key {
        const val APP_VERSION = "APP_VERSION"
        const val APP_VERSION_NAME = "APP_VERSION_NAME"
        const val IS_UP_TO_DATE = "IS_UP_TO_DATE"
        const val TOKEN = "TOKEN"
        const val USERNAME = "USERNAME"
        const val EMAIL = "EMAIL"
        const val TITLE = "TITLE"
        const val BODY = "BODY"
    }
}