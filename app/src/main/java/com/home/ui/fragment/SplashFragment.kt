package com.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anull.R
import com.example.anull.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private val splashTimeOut: Long = 3000
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    private fun changeTopOfScreen(sw: Int) {
        if (sw == 0) {
            val reqWin = requireActivity().window
            reqWin.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            reqWin.statusBarColor = Color.TRANSPARENT

        } else if (sw == 1) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            requireActivity().window.statusBarColor = Color.parseColor("#813ac1")
        }

//        requireActivity().window.decorView.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        // requireActivity().window.statusBarColor = Color.parseColor("#813ac1")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val window = requireActivity().window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        Handler().postDelayed({
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignUpFragment())
                changeTopOfScreen(0)
        }, splashTimeOut)

    }
}