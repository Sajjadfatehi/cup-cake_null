package com.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.activity.MainActivity
import com.config.FunctionHelper
import com.example.anull.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener {

    var username: String = ""
    var password: String = ""
    var mainActivity: MainActivity ?= null
    val functionHelper :FunctionHelper = FunctionHelper()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.window?.attributes?.softInputMode
        tv_go_to_be_member.setOnClickListener(this)
        btn_login.setOnClickListener(this)


        SignUpFragment.textChange(edt_user, edt_user_inputLayout)
        SignUpFragment.textChange(passLogin, passLoginInputLayout)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                checkData()
            }
            R.id.tv_go_to_be_member -> {
                val signUpFragment = SignUpFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, signUpFragment).commit()
            }
        }
    }

    private fun checkData() {
        username = edt_user.text.toString()
        password = passLogin.text.toString()

        if (username.trim().isEmpty()) {
            edt_user_inputLayout.error = "خطا! این کادر را پر کنید"
        } else if (password.trim().isEmpty()) {
            passLoginInputLayout.error = "خطا! این کادر را پر کنید"
        }
        else {
            val homeFragment = HomeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame, homeFragment).commit()        }
        functionHelper.getPublicSharedPreferences(context)?.setToken(passLogin.text.toString())


    }
}