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


        Handler().postDelayed({
            changeTopOfScreen()

            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignUpFragment())

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