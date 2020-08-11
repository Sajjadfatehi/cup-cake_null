package com.example.anull

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
            Toast.makeText(this, "grgesgeg", Toast.LENGTH_SHORT).show()
            supportFragmentManager.beginTransaction().remove(splashFragment).commit()

            supportFragmentManager.beginTransaction().replace(R.id.splashFrame, signUpFragment)
                .commit()


        }, splashTimeOut)


    }
}