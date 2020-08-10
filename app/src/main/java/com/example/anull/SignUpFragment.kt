package com.example.anull

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SignUpFragment : Fragment() {
    lateinit var goLoginScreen: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        goLoginScreen = view.findViewById(R.id.tv_go_login_screen)
        goLoginScreen.setOnClickListener {
            val loginFragment = LoginFragment()
            // activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.splashFrame,loginFragment)?.commit()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.splashFrame, loginFragment).commit()
        }


        return view
    }

}