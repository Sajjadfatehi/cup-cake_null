package com.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adapter.ArticleAdapter
import com.example.anull.R
import com.model.home.PersonArticleModel
import kotlinx.android.synthetic.main.fragment_title.*

class TitleFragment : Fragment() {
    private var list = mutableListOf<PersonArticleModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        repeat(20) {
            list.add(
                PersonArticleModel(
                    "محمد",
                    "دو روز قبل",
                    true,
                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
                )
            )
        }
        recyclerTitle.apply {
            adapter = ArticleAdapter(list)
        }

    }
}