package com.article.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.article.ui.viewmodel.WriteArticleViewModel
import com.example.anull.R
import com.example.anull.databinding.FragmentWriteArticleBinding
import com.user.ui.PostInProfView
import kotlinx.android.synthetic.main.fragment_write_article.*


class WriteArticleFragment : Fragment() {

    var args: Bundle = Bundle()
    private lateinit var writeViewModel: WriteArticleViewModel
    private lateinit var binding: FragmentWriteArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        writeViewModel = ViewModelProvider(this).get(WriteArticleViewModel::class.java)
        args = this.requireArguments()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_write_article, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        writeViewModel.checkArgsIsNull(args)
        writeViewModel.activity = requireActivity()

        binding.writeViewModel = writeViewModel
        binding.lifecycleOwner = this

        writeViewModel.isFromEdit.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { isFromEdit ->
                if (isFromEdit) {

                    val post = args.getParcelable<PostInProfView>("post")
                    val itemSelectedForEdit = args.getInt("number")

                    submit_article.setOnClickListener {
                        val bundle = Bundle()
                        post?.title = edit_title.text.toString().trim()
                        post?.desc = edit_text.text.toString().trim()
                        //add keyword later

                        bundle.putParcelable("editPost", post)
                        if (itemSelectedForEdit != null) {
                            bundle.putInt("numberOfEditPost", itemSelectedForEdit)
                        }
                        findNavController().navigate(
                            R.id.action_writeArticleFragment_to_profileFragment,
                            bundle
                        )
                    }
                }
            })

        binding.arrowBackWA.setOnClickListener {
            hideKeyboard()
            findNavController().navigateUp()
        }

        binding.setTagEdt.setOnFocusChangeListener { _, b ->
            if (b)
                setAdjustPan()
        }

    }

    private fun setAdjustPan() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun hideKeyboard() {
        val view = requireActivity().currentFocus
        view?.let { v ->
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            //below code is for when user click on edit text , adjust pan call again
            setTagEdt.clearFocus()
        }
    }

}

