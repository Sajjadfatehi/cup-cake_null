package com.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.config.Setting
import com.example.anull.R
import com.fragment.HomeFragment
import com.fragment.SignUpFragment
import com.fragment.SplashFragment

class MainActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val splashFragment = SplashFragment()
        val signUpFragment = SignUpFragment()
        supportFragmentManager.beginTransaction().replace(R.id.splashFrame, splashFragment).commit()

        Handler().postDelayed({
            val window = window

            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            supportFragmentManager.beginTransaction().remove(splashFragment).commit()
            supportFragmentManager.beginTransaction().replace(R.id.splashFrame, signUpFragment)
                .commit()
        }, splashTimeOut)


    }


}