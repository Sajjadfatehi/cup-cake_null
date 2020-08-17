package com.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.config.FunctionHelper
import com.example.anull.R
<<<<<<< HEAD
import com.fragment.HomeFragment
import com.fragment.LoginFragment
import com.fragment.SignUpFragment
import com.fragment.SplashFragment
=======
>>>>>>> add navigation on project

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

<<<<<<< HEAD
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
=======

//        val splashFragment = SplashFragment()
//        val signUpFragment = SignUpFragment()
//        supportFragmentManager.beginTransaction().replace(R.id.splashFrame, splashFragment).commit()
//

//        Handler().postDelayed({
//
//
//            changeTopOfScreen()
//            supportFragmentManager.beginTransaction().remove(splashFragment).commit()
//            supportFragmentManager.beginTransaction().replace(R.id.splashFrame, signUpFragment)
//                .commit()
//        }, splashTimeOut)

>>>>>>> add navigation on project

    }
//
//    fun changeTopOfScreen() {
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = Color.TRANSPARENT
//
//    }


}