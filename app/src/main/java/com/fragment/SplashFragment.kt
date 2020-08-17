package com.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anull.R

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashFragment : Fragment() {
    private val splashTimeOut: Long = 3000
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)


        val window = requireActivity().window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

//java code for example
//        Window window = getWindow();
//        window.getDecorView().setSystemUiVisibility(
//            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.TRANSPARENT);

        val signUpFragment = SignUpFragment()


        Handler().postDelayed({


            changeTopOfScreen()

            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignUpFragment())

//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.splashFrame, signUpFragment)
//                .commit()
        }, splashTimeOut)






        return view
    }

    fun changeTopOfScreen() {
        val reqWin = requireActivity().window
        reqWin.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        reqWin.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        reqWin.statusBarColor = Color.TRANSPARENT

    }

}