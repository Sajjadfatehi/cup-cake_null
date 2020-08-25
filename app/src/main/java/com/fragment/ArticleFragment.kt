package com.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adapter.CommentAdapter
import com.adapter.RelatedArticleAdapter
import com.example.anull.R
import com.example.anull.databinding.ArticleFragmentBinding
import com.model.article.CommentArticleModel
import com.model.article.RelatedArticleModel
import kotlinx.android.synthetic.main.article_fragment.*

/**
Created by Moha.Azizi on 18/08/2020 .
 */
class ArticleFragment : Fragment() {
    private var list = mutableListOf<CommentArticleModel>()
    private var list2 = mutableListOf<RelatedArticleModel>()
    private lateinit var binding: ArticleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.article_fragment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        radioGroup.setOnCheckedChangeListener { _, i ->
            val radio = requireActivity().findViewById<RadioButton>(i)
            // Toast.makeText(requireContext(),"${radio.text}",Toast.LENGTH_SHORT).show()
            if (radio.tag == "posts") {
                radio.setTextColor(Color.parseColor("#813ac1"))
                requireActivity().findViewById<RadioButton>(R.id.radio2)
                    .setTextColor(Color.parseColor("#363636"))
            } else {
                radio.setTextColor(Color.parseColor("#813ac1"))
                requireActivity().findViewById<RadioButton>(R.id.radio1)
                    .setTextColor(Color.parseColor("#363636"))

            }
        }

        repeat(20) {
            list.add(
                CommentArticleModel(
                    "محمد",
                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
                )
            )
        }
        recycler_article_related.apply {
            adapter = CommentAdapter(list)
        }
        repeat(20) {
            list2.add(
                RelatedArticleModel(
                    "محمد", "دوروز ثبل", true,
                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
                )
            )
        }
        recycler_comment.apply {
            adapter = RelatedArticleAdapter(list2)
        }
        button_comment.setOnClickListener {
            findNavController().navigate(ArticleFragmentDirections.actionArticleFragmentToCommentDialogFragment())
        }

    }
}