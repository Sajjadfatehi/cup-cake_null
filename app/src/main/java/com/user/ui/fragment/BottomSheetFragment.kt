package com.user.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.anull.R
import com.example.anull.databinding.BottomSheetBindingImpl
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetBindingImpl
    interface CallBack {
        fun onCall(action: String, numberOfItem: Int?)
    }

    lateinit var iCall: CallBack
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.bottom_sheet, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args = arguments

        args?.getBoolean("isMyOwnPage")?.let { handleVisibilityOfButtons(it) }

        edit_article_btn.setOnClickListener {

            val number = args?.getInt("layoutPosition")
            iCall.onCall("edit", number)


        }
        delete_article_btn.setOnClickListener {
            val number = args?.getInt("layoutPosition")
            iCall.onCall("delete", number)

        }
        share_article_btn.setOnClickListener {
            val number = args?.getInt("layoutPosition")
            iCall.onCall("share", number)
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

    private fun handleVisibilityOfButtons(isMyOwnPage: Boolean) {
        if (!isMyOwnPage) {
            edit_article_btn.visibility = View.GONE
            delete_article_btn.visibility = View.GONE
        }
    }


}
