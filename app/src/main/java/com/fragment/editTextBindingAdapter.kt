package com.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter(value = ["bind:inputTextLayout"], requireAll = true)
fun checkNotEmptyEditTexts(view: View, inputText: View) {
    inputText as TextInputLayout
    view as TextInputEditText

    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (s.isNotEmpty() && inputText.isErrorEnabled) inputText.isErrorEnabled =
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
                inputText.error = "خطا! این کادر نباید خالی باشد "

            }
        }

    })

}


// helper function for see that the text type inside email edit text is valid
fun isValid(email: String): Boolean {
    if (email.lastIndexOf('@') <= 0 || !email.contains(".")
        || email.lastIndexOf('@') < email.lastIndexOf('@')
        || email.split("@").size > 2
    ) return false
    return true

}


@BindingAdapter(value = ["bind:emailInputTextLayout"], requireAll = true)
fun handleErrorForEmail(view: View, emailInputText: View) {
    view as TextInputEditText
    emailInputText as TextInputLayout

    var emailText = view.text
    view.setOnFocusChangeListener { _, b ->
        if (!b) {
            if (emailText.isNullOrEmpty()) emailInputText.error =
                "خطا! این کادر نباید خالی باشد"
            else if (!isValid(emailText.toString().trim()))

                emailInputText.error = "قالب ایمیل وارد شده نامعتبر است"
        }

    }


}


@BindingAdapter(value = ["bind:passInputTextLayout"], requireAll = true)
fun handleErrorForPassword(view: View, passInputTextLayout: View) {
    view as TextInputEditText
    passInputTextLayout as TextInputLayout
    var passText = view.text
    view.setOnFocusChangeListener { _, b ->
        if (!b && !passText.isNullOrEmpty() && passText.toString()
                .trim().length < 8
        ) {
            passInputTextLayout.error = "تعداد حروف وارد شده حداقل باید 8 عدد باشد !"
        }

    }
}


@BindingAdapter(
    value = ["bind:repetitionPasswordInputTextLayout", "bind:passEditText"],
    requireAll = true
)
fun handleErrorForRepetitionPassword(
    view: View,
    repetitionPasswordInputTextLayout: View,
    passEditText: View
) {
    view as TextInputEditText
    repetitionPasswordInputTextLayout as TextInputLayout
    passEditText as TextInputEditText


    var passText = passEditText.text
    var repetitionPassText = view.text
    view.setOnFocusChangeListener { _, b ->
        if (!b) {

            if (!repetitionPassText.isNullOrEmpty() && repetitionPassText.toString()
                    .trim().length < 8
            ) {
                repetitionPasswordInputTextLayout.error =
                    "تعداد حروف وارد شده حداقل باید 8 عدد باشد !"
            } else if (!passText.isNullOrEmpty() &&
                !passText.toString().trim().equals(repetitionPassText.toString().trim())
            ) {
                repetitionPasswordInputTextLayout.error =
                    "پسورد وارد شده با پسوردی که قبلا وارد شد یکسان نیست ! "
            }
        }


    }
}
