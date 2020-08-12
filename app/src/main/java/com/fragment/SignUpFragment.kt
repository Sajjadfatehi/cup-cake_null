package com.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anull.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_go_login_screen.setOnClickListener {
            val loginFragment = LoginFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.splashFrame, loginFragment).commit()
        }


        emailEditText.setOnFocusChangeListener { view, b ->
            if (!b) {
                if (emailEditText.text.isNullOrEmpty()) emailInputLayout.error =
                    "خطا! این کادر نباید خالی باشد"
                else if (!isValid(emailEditText.text.toString().trim()))

                    emailInputLayout.error = "قالب ایمیل وارد شده نامعتبر است"
            }

        }
        passSignUp.setOnFocusChangeListener { view, b ->
            if (!b && !passSignUp.text.isNullOrEmpty() && passSignUp.text.toString()
                    .trim().length < 8
            ) {
                passSignUpInputLayout.error = "تعداد حروف وارد شده حداقل باید 8 عدد باشد !"
            }

        }
        repetitionPass.setOnFocusChangeListener { view, b ->
            if (!b) {
                if (!repetitionPass.text.isNullOrEmpty() && repetitionPass.text.toString()
                        .trim().length < 8
                ) {
                    repetitionPassInputLayout.error = "تعداد حروف وارد شده حداقل باید 8 عدد باشد !"
                } else if (!passSignUp.text.isNullOrEmpty() &&
                    !passSignUp.text.toString().trim().equals(repetitionPass.text.toString().trim())
                ) {
                    repetitionPassInputLayout.error =
                        "پسورد وارد شده با پسوردی که قبلا وارد شد یکسان نیست ! "
                }
            }


        }


        textChange(userNameEditText, userNameInputLayout)
        textChange(passSignUp, passSignUpInputLayout)
        textChange(repetitionPass, repetitionPassInputLayout)
        textChange(emailEditText, emailInputLayout)


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

}