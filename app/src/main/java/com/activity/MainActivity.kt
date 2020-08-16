package com.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.config.FunctionHelper
import com.example.anull.R
import com.fragment.HomeFragment
import com.fragment.LoginFragment
import com.fragment.SignUpFragment
import com.fragment.SplashFragment

class MainActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 3000 // 1 sec
    val splashFragment = SplashFragment()
    val signUpFragment = SignUpFragment()
    val homeFragment = HomeFragment()
    val loginFragment = LoginFragment()
    val functionHelper: FunctionHelper = FunctionHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginFragment.mainActivity = this

        supportFragmentManager.beginTransaction().replace(R.id.frame, splashFragment).commit()
        Handler().postDelayed({
            changeTopOfScreen()
            if (functionHelper.getPublicSharedPreferences(this)?.getToken()!!.isEmpty()) {
                supportFragmentManager.beginTransaction().remove(splashFragment).commit()
                supportFragmentManager.beginTransaction().replace(R.id.frame, signUpFragment)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction().remove(splashFragment).commit()
                supportFragmentManager.beginTransaction().replace(R.id.frame, homeFragment)
                    .commit()
            }

        }, splashTimeOut)


    }

    fun changeTopOfScreen() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    }


}