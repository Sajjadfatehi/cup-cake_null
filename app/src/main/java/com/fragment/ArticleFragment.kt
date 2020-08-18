package com.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adapter.CommentAdapter
import com.adapter.RelatedArticleAdapter
import com.example.anull.R
import com.model.article.CommentArticleModel
import com.model.article.RelatedArticleModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
Created by Moha.Azizi on 18/08/2020 .
 */
class ArticleFragment : Fragment() {
    private var list = mutableListOf<CommentArticleModel>()
    private var list2 = mutableListOf<RelatedArticleModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeat(20) {
            list.add(
                CommentArticleModel(
                    "محمد",
                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
                )
            )
        }
        recycler_person_article.apply {
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
        recycler_best_article.apply {
            adapter = RelatedArticleAdapter(list2)
        }
    }
}