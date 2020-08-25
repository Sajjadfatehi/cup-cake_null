package com.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anull.R
import com.example.anull.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoLoginScreen.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }



        HandleError().handleErrorForEmail()
        HandleError().handleErrorForPassword()
        HandleError().handleErrorForRepetitionPassword()



        textChange(userNameEditText, userNameInputLayout)
        textChange(passSignUp, passSignUpInputLayout)
        textChange(repetitionPass, repetitionPassInputLayout)
        textChange(emailEditText, emailInputLayout)
        tvMemberShip.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }

        singUpButton.setOnClickListener {


            //below line is for app bar layout that don,t go behind the status bar
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            requireActivity().window.statusBarColor = Color.parseColor("#813ac1")
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())

        }


    }


    companion object {
        fun isValid(email: String): Boolean {
            if (email.lastIndexOf('@') <= 0 || !email.contains(".")
                || email.lastIndexOf('@') < email.lastIndexOf('@')
                || email.split("@").size > 2
            ) return false
            return true

        }

        fun textChange(editText: TextInputEditText, inputLayout: TextInputLayout) {

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty() && inputLayout.isErrorEnabled) inputLayout.isErrorEnabled =
                        false
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.isEmpty()) {
                        inputLayout.error = "خطا! این کادر نباید خالی باشد "

                    }
                }

            })
        }


    }

    inner class HandleError {
        fun handleErrorForEmail() {
            var emailText = emailEditText.text
            emailEditText.setOnFocusChangeListener { view, b ->
                if (!b) {
                    if (emailText.isNullOrEmpty()) emailInputLayout.error =
                        "خطا! این کادر نباید خالی باشد"
                    else if (!isValid(emailText.toString().trim()))

                        emailInputLayout.error = "قالب ایمیل وارد شده نامعتبر است"
                }

            }

        }

        fun handleErrorForPassword() {
            var passText = passSignUp.text
            passSignUp.setOnFocusChangeListener { view, b ->
                if (!b && !passText.isNullOrEmpty() && passText.toString()
                        .trim().length < 8
                ) {
                    passSignUpInputLayout.error = "تعداد حروف وارد شده حداقل باید 8 عدد باشد !"
                }

            }
        }

        fun handleErrorForRepetitionPassword() {
            var passText = passSignUp.text
            var repetitionPassText = repetitionPass.text
            repetitionPass.setOnFocusChangeListener { view, b ->
                if (!b) {

                    if (!repetitionPassText.isNullOrEmpty() && repetitionPassText.toString()
                            .trim().length < 8
                    ) {
                        repetitionPassInputLayout.error =
                            "تعداد حروف وارد شده حداقل باید 8 عدد باشد !"
                    } else if (!passText.isNullOrEmpty() &&
                        !passText.toString().trim().equals(repetitionPassText.toString().trim())
                    ) {
                        repetitionPassInputLayout.error =
                            "پسورد وارد شده با پسوردی که قبلا وارد شد یکسان نیست ! "
                    }
                }


            }
        }


    }


}