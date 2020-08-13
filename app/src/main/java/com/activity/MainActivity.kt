package com.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.anull.R
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


            changeTopOfScreen()
            supportFragmentManager.beginTransaction().remove(splashFragment).commit()
            supportFragmentManager.beginTransaction().replace(R.id.splashFrame, signUpFragment)
                .commit()
        }, splashTimeOut)


    }

    fun changeTopOfScreen() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT

    }


}