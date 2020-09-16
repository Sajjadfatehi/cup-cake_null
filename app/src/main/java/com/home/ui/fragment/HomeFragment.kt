package com.home.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.article.data.ArticleRepository
import com.article.data.TagModel
import com.example.anull.R
import com.example.anull.databinding.FragmentHomeBinding
import com.home.data.HomeRepasitory
import com.home.data.HomeViewModel
import com.home.data.PersonArticleModelEntity
import com.home.data.TabModelEntity
import com.home.ui.adapter.BestArticleAdapter
import com.home.ui.adapter.PersonArticleAdapter
import kotlinx.android.synthetic.main.fragment_home.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var backAgain = false


    private lateinit var binding: FragmentHomeBinding
    private var list = mutableListOf<PersonArticleModelEntity>()
    private var tabs: ArrayList<TabModelEntity> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event

                    if (backAgain) {
                        activity?.finish()
                    } else {
                        backAgain = true
                        Toast.makeText(
                            requireContext(),
                            context?.resources?.getString(R.string.back_message),
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler().postDelayed({
                            backAgain = false
                        }, 2000)

                    }

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //------------------------------------------------
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        val homeViewModel = HomeViewModel(repo = ArticleRepository())
        homeViewModel.getAllTags()
        homeViewModel.tags.observe(viewLifecycleOwner, Observer {
            setTabs(it)
        })

        binding.icProfile.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())

        }
        binding.add.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment())
        }

        repeat(20) {
            list.add(
                PersonArticleModelEntity(
                    "محمد",
                    "دو روز قبل",
                    true,
                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
                )
            )
        }

        recycler_person_article.apply {
            adapter = PersonArticleAdapter(list)
        }
        recycler_best_article.apply {
            adapter = BestArticleAdapter(list)
        }


    }

    private fun setTabs(it: List<TagModel>?) {

        for (element in it!!) {
            tabLayout.addTab(tabLayout.newTab().setText(element.title))
        }

    }


}
