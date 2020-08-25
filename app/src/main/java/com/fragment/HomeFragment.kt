package com.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adapter.BestArticleAdapter
import com.adapter.PersonArticleAdapter
import com.example.anull.R
import com.example.anull.databinding.FragmentHomeBinding
import com.model.home.PersonArticleModel
import com.model.home.TabModel
import kotlinx.android.synthetic.main.fragment_home.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var backAgain = false

    private lateinit var binding: FragmentHomeBinding
    private var list = mutableListOf<PersonArticleModel>()
    private var tabs: ArrayList<TabModel> = ArrayList()
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
                            "برای بازگشت دوباره دکمه خروج را فشار دهید",
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //------------------------------------------------
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE

//        setTabs()

        binding.icProfile.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())

        }
        binding.add.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment())
        }

        repeat(20) {
            list.add(
                PersonArticleModel(
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


//    private fun setTabs() {
//        tabs.add(TabModel(getString(R.string.foryou)))
//        tabs.add(TabModel(getString(R.string.bors)))
//        tabs.add(TabModel(getString(R.string.saham)))
//        tabs.add(TabModel(getString(R.string.eqtsad)))
//        tabs.add(TabModel(getString(R.string.sarmaye)))
//
//        for (i in 0 until tabs.size) {
//            tabLayout.addTab(tabLayout.newTab().setText(tabs[i].name))
//        }

}
//}