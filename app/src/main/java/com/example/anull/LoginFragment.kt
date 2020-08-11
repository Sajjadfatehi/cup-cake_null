package com.example.anull

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class LoginFragment : Fragment() {
    lateinit var goToBeMember: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        goToBeMember = view.findViewById(R.id.tv_go_to_be_member)
        goToBeMember.setOnClickListener {
            val signUpFragment = SignUpFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.splashFrame, signUpFragment).commit()

        }


        return view
    }

}