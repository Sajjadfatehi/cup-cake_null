package com.example.anull

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long=3000 // 3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
            Handler().postDelayed({
                // This method will be executed once the timer is over
                // Start your app main activity

                startActivity(Intent(this,MainActivity::class.java))

                // close this activity
                finish()
            }, SPLASH_TIME_OUT)
        }

}