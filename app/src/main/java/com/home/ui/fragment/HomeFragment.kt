package com.home.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.article.data.ArticleRepository
import com.article.data.ArticleUser
import com.article.data.TagModel
import com.article.data.localdatasource.ArticleLocalDataSource
import com.article.data.remotedatasource.ArticleRemoteDataSource
import com.core.db.AppDataBase
import com.example.anull.R
import com.example.anull.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.home.data.HomeViewModel
import com.home.data.PersonArticleModelEntity
import com.home.data.TabModelEntity
import com.home.ui.adapter.BestArticleAdapter
import com.home.ui.adapter.PersonArticleAdapter
import com.user.data.UserEntity
import com.user.data.modelfromservice.Author
import kotlinx.android.synthetic.main.fragment_home.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private var param1: String? = null
    private var param2: String? = null
    var backAgain = false
    private lateinit var homeViewModel: HomeViewModel

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE articles ADD COLUMN test TEXT")
        }
    }
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
        val articleLocalDataSource= ArticleLocalDataSource(AppDataBase.invoke(requireContext(),MIGRATION_1_2))
        val articleRemoteDataSource= ArticleRemoteDataSource()
        homeViewModel = HomeViewModel(repo = ArticleRepository(articleLocalDataSource,articleRemoteDataSource))
        homeViewModel.getAllTags()
        //------------------------------
//        homeViewModel.getArticleWithTag()
        //------------------------------
        binding.tabLayout.addOnTabSelectedListener(this)
        //------------------------------
        homeViewModel.tags.observe(viewLifecycleOwner, Observer {
            setTabs(it)
        })
        //-----------------------------
        homeViewModel.articles.observe(viewLifecycleOwner, Observer {
            updateList(it)
        })

        binding.icProfile.setOnClickListener {

            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                    UserEntity(
                        "44444444",
                        null,
                        false,
                        ""
                    )
                )
            )

        }
        binding.add.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment())
        }

//        repeat(20) {
//            list.add(
//                PersonArticleModelEntity(
//                    "محمد",
//                    "دو روز قبل",
//                    true,
//                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
//                )
//            )
//        }

        recycler_person_article.apply {
            (binding.recyclerPersonArticle.adapter as? PersonArticleAdapter)
            adapter = PersonArticleAdapter().also {
                it.setList(listOf())
            }
        }
        recycler_best_article.apply {
            (binding.recyclerPersonArticle.adapter as? BestArticleAdapter)
            adapter = BestArticleAdapter().also {
                it.setList(listOf())
            }
        }


    }

    private fun updateList(list: List<ArticleUser>) {
        (binding.recyclerPersonArticle.adapter as? PersonArticleAdapter)?.setList(list)
        list.sortedBy {
            it.articleDataEntity.favoritesCount

        }
        if (list.isEmpty()) {
            binding.recyclerBestArticle.isInvisible
            binding.textBestArticle.isInvisible
        }
        (binding.recyclerBestArticle.adapter as? BestArticleAdapter)?.setList(list)
    }

    private fun setTabs(it: List<TagModel>?) {

        for (element in it!!) {
            tabLayout.addTab(tabLayout.newTab().setText(element.title))
        }

    }


    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (homeViewModel.getArticleWithTag(tab?.text.toString())
                .equals(resources.getString(R.string.for_you))
        ) {
            homeViewModel.getArticleWithTag(tab?.text.toString())
        } else {
            homeViewModel.getArticleWithTag(tab?.text.toString())
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        Log.i("onTabSelected", "onTabSelected: $tab")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        Log.i("onTabSelected", "onTabSelected: $tab")
    }


}
