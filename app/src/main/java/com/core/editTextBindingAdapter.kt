package com.core

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.config.MyApp
import com.example.anull.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*


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


@BindingAdapter(value = ["bind:chipGroup", "bind:ListOfChip"], requireAll = false)
fun chipInWriteArticle(view: View, chipGroup: View, tagsChip: MutableMap<Chip, String>) {
    chipGroup as ChipGroup
    view as EditText


    view.addTextChangedListener(object : TextWatcher, View.OnClickListener {
        override fun afterTextChanged(s: Editable) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            if (s.trim().isNotEmpty() && s.length != s.trimEnd().length) {
                //   val chip= Chip(tagChipGroup.context, null,R.style.chip_style)
                view.setText("")
                val chip = LayoutInflater.from(view.context)
                    .inflate(R.layout.single_chip, chipGroup, false) as Chip
                chip.isClickable = false
                chip.isCheckable = false
                chip.setOnCloseIconClickListener(this)


                chip.text = s.trim()
                val tag = chip.text.toString().toUpperCase(Locale.ROOT)
                if (!tagsChip.containsValue(tag)) {
                    chipGroup.addView(chip)
                    tagsChip[chip] = tag
                }


                //below code is for
                hideKeyboard(view)


            }

        }

        override fun onClick(v: View?) {
            val chip = v as Chip
            chipGroup.removeView(chip)
            tagsChip.remove(chip)

        }

    })

}


private fun hideKeyboard(view: EditText) {
    val context = MyApp.app
    view.let { v ->
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
        //below code is for when user click on edit text , adjust pan call again
        view.clearFocus()
    }
}
/*private fun setAdjustPan() {
    val activity= MyApp.app as Activity
    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)}*/