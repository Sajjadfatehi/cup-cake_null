package com.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.anull.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment() {
    interface CallBack {

        fun onCall(action: String, numberOfItem: Int?)
    }

    lateinit var iCall: CallBack
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args = arguments

        edit_article_btn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "${args?.getInt("layoutPosition")}",
                Toast.LENGTH_SHORT
            ).show()
            val number = args?.getInt("layoutPosition")
            iCall.onCall("edit", number)


        }
        delete_article_btn.setOnClickListener {
            val number = args?.getInt("layoutPosition")
            iCall.onCall("delete", number)

        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            iCall = parentFragment as CallBack
        } catch (e: Exception) {
            //handle exception
        }
    }


}
