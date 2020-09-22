package com.article.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.article.data.ArticleRepository
import com.article.data.localdatasource.ArticleLocalDataSource
import com.article.data.remotedatasource.ArticleRemoteDataSource
import com.article.ui.adapter.ArticleAdapter
import com.article.ui.adapter.ArticleAdapterDiff
import com.article.ui.viewmodel.TitleViewModel
import com.article.ui.viewmodel.providerfactory.TitleViewModelProviderFactory
import com.core.db.AppDataBase
import com.core.util.Constants
import com.core.util.Resource
import com.core.util.TestAdapterClass
import com.example.anull.R
import com.example.anull.databinding.FragmentTitleBinding
import kotlinx.android.synthetic.main.fragment_title.*
import kotlinx.android.synthetic.main.testlayout.*

class TitleFragment : Fragment() {
    lateinit var titleAdapterDiff:ArticleAdapterDiff
    private lateinit var binding: FragmentTitleBinding
    private lateinit var viewModel: TitleViewModel
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE articles ADD COLUMN test TEXT")
        }
    }

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


        val articleLocalDataSource= ArticleLocalDataSource(AppDataBase.invoke(requireContext(),MIGRATION_1_2))
        val articleRemoteDataSource= ArticleRemoteDataSource()
        val articleRepository=ArticleRepository(articleLocalDataSource,articleRemoteDataSource)
        val titleViewModelProvider=TitleViewModelProviderFactory(articleRepository)

        viewModel = ViewModelProvider(this,titleViewModelProvider).get(TitleViewModel::class.java)
//        recyclerTitle.apply {
//            adapter =
//                viewModel.getArticle()?.let { ArticleAdapter(it) }
//            //   adapter=ArticleAdapter(list)
//        }

//        viewModel.list.observe(viewLifecycleOwner, Observer { list ->
//            recyclerTitle.adapter?.notifyDataSetChanged()
//        })
        setUpRecyclerView()
        viewModel.articlesByTag.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success<*> -> {
                    hideProgressBar()
                    response.data?.let { articlesByTag ->
                        titleAdapterDiff.differ.submitList(articlesByTag.articles.toList())
                        val totalPages = articlesByTag.articlesCount / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = totalPages == viewModel.articlesByTagPage

                        if (isLastPage){
                            recyclerTitle.setPadding(0,0,0,0)
                        }

                    }
                }
                is Resource.Error<*> -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("ProfileFragment ", "an error happened: $message ")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }


            }

        })




        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

    }


    private fun hideProgressBar() {
        tagProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        tagProgressBar.visibility = View.VISIBLE
        isLoading = true
    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerTitle.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            //momkene badan be moshkel bokhore chon bad az yekam scroll recycler tamame sfhe ro miigre
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getArticlesByTag("dragons")
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }


    private fun setUpRecyclerView() {
        titleAdapterDiff = ArticleAdapterDiff()
        recyclerTitle.apply {
            setHasFixedSize(true)
            adapter = titleAdapterDiff
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@TitleFragment.scrollListener)
        }
    }
}