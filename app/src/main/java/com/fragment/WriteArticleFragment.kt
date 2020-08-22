package com.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anull.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_write_article.*


class WriteArticleFragment : Fragment() {

    val tagsChip = mutableMapOf<Chip, String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_write_article, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrow_back_WA.setOnClickListener {
            findNavController().navigate(WriteArticleFragmentDirections.actionWriteArticleFragmentToHomeFragment())

        }
        addTagHandle(setTagEdt)
    }

    private fun addTagHandle(editText: EditText) {

        editText.addTextChangedListener(object : TextWatcher, View.OnClickListener {
            override fun afterTextChanged(s: Editable) {
//
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.trim().isNotEmpty() && s.length != s.trimEnd().length) {
                    //   val chip= Chip(tagChipGroup.context, null,R.style.chip_style)
                    editText.setText("")
                    val chip =
                        layoutInflater.inflate(R.layout.single_chip, tagChipGroup, false) as Chip
                    chip.isClickable = false
                    chip.isCheckable = false
                    chip.setOnCloseIconClickListener(this)
                    chip.text = s.trim()
                    val tag = chip.text.toString().toUpperCase()
                    if (!tagsChip.containsValue(tag)) {
                        tagChipGroup.addView(chip)
                        tagsChip.put(chip, tag)
                    }
                    val view = requireActivity().currentFocus
                    view?.let { v ->
                        val imm =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm?.hideSoftInputFromWindow(v.windowToken, 0)
                    }
                }

            }

            override fun onClick(v: View?) {
                val chip = v as Chip
                tagChipGroup.removeView(chip)
                tagsChip.remove(chip)

            }

        })
    }

}

