package com.config

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.BuildConfig
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.storage.data.PreferenceProperty.Companion.APP_PREF_NAME


class MyApp : Application() {
    companion object {
        val networkFlipperPlugin = NetworkFlipperPlugin()
        lateinit var app: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.addPlugin(networkFlipperPlugin)
            client.addPlugin(SharedPreferencesFlipperPlugin(this, APP_PREF_NAME))
            client.start()
        }
    }
}