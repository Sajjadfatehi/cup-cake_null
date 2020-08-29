package com.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anull.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.model.PostInProf
import java.util.*


class WriteArticleViewModel : ViewModel() {


    var activity: FragmentActivity = FragmentActivity()

    var argsFromProf: Bundle = Bundle()
    var isFromEdit = MutableLiveData<Boolean>()

    lateinit var postInProf: PostInProf

    companion object {

        val tagsChip = mutableMapOf<Chip, String>()

        @BindingAdapter(value = ["bind:chipGroup"], requireAll = false)
        @JvmStatic
        fun chipInWriteArticle(view: View, chipGroup: View) {

            chipGroup as ChipGroup
            view as EditText
            view.addTextChangedListener(object : TextWatcher, View.OnClickListener {
                override fun afterTextChanged(s: Editable) {
//
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

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
                        // hideKeyboard()


                    }

                }

                override fun onClick(v: View?) {
                    val chip = v as Chip
                    chipGroup.removeView(chip)
                    tagsChip.remove(chip)

                }

            })

        }

//
//         fun hideKeyboard(writeArticleViewModel: WriteArticleViewModel) {
//            val view = writeArticleViewModel.activity.currentFocus
//            view?.let { v ->
//                val imm =
//                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(v.windowToken, 0)
//                //below code is for when user click on edit text , adjust pan call again
//                setTagEdt.clearFocus()
//            }
//        }
    }


    fun checkArgsIsNull(bundle: Bundle?) {

        if (bundle != null) {
            argsFromProf = bundle
        }
        if (!argsFromProf.isEmpty) {
            postInProf = argsFromProf.getParcelable<PostInProf>("post")!!
        }
        isFromEdit.value = bundle != null


    }


}



