package com.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adapter.ArticleAdapter
import com.example.anull.R
import com.example.anull.databinding.FragmentTitleBinding
import kotlinx.android.synthetic.main.fragment_title.*

class TitleFragment : Fragment() {
    private lateinit var binding: FragmentTitleBinding
    private lateinit var viewModel: TitleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
        recyclerTitle.apply {
            adapter = ArticleAdapter(viewModel.getArticle().value!!)
            //   adapter=ArticleAdapter(list)
        }

        viewModel.list.observe(viewLifecycleOwner, Observer { list ->
            recyclerTitle.adapter?.notifyDataSetChanged()
        })



        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}