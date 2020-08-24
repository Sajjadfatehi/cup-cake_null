package com.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.config.FunctionHelper
import com.example.anull.R
import com.example.anull.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener {

    var username: String = ""
    var password: String = ""
    private val functionHelper = FunctionHelper()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.window?.attributes?.softInputMode
        binding.tvGoToBeMember.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)


        SignUpFragment.textChange(edt_user, edt_user_inputLayout)
        SignUpFragment.textChange(passLogin, passLoginInputLayout)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                checkData()
            }
            R.id.tv_go_to_be_member -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }
        }
    }

    private fun checkData() {
        username = edt_user.text.toString()
        password = passLogin.text.toString()

        when {
            username.trim().isEmpty() -> {
                edt_user_inputLayout.error = "خطا! این کادر را پر کنید"
            }
            password.trim().isEmpty() -> {
                passLoginInputLayout.error = "خطا! این کادر را پر کنید"
            }
            else -> {
                functionHelper.getPublicSharedPreferences(context)?.setToken(passLogin.text.toString())
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        }
    }
}