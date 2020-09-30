package com.article.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.article.data.ArticleRepository
import com.article.data.ArticleUser
import com.article.data.localdatasource.ArticleLocalDataSource
import com.article.data.remotedatasource.ArticleRemoteDataSource
import com.article.ui.adapter.ArticleAdapterDiff
import com.article.ui.viewmodel.TagClickListener
import com.article.ui.viewmodel.TitleViewModel
import com.article.ui.viewmodel.providerfactory.TitleViewModelProviderFactory
import com.core.ResultCallBack
import com.core.db.AppDataBase
import com.example.anull.databinding.FragmentTitleBinding
import com.google.android.material.snackbar.Snackbar
import com.user.data.UserEntity
import kotlinx.android.synthetic.main.fragment_title.*
import kotlinx.android.synthetic.main.testlayout.*

class TitleFragment : Fragment(), TagClickListener, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE articles ADD COLUMN test TEXT")
            }
        }
    }

    private lateinit var binding: FragmentTitleBinding
    private lateinit var viewModel: TitleViewModel
    private lateinit var titleAdapterDiff: ArticleAdapterDiff



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTitleBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tagSwipeRefresh.setOnRefreshListener(this)

        var tag = "بورس"


        val articleLocalDataSource =
            ArticleLocalDataSource(AppDataBase.invoke(requireContext(), MIGRATION_1_2))
        val articleRemoteDataSource = ArticleRemoteDataSource()
        val articleRepository = ArticleRepository(articleLocalDataSource, articleRemoteDataSource)
        val titleViewModelProvider = TitleViewModelProviderFactory(articleRepository, tag)

        viewModel = ViewModelProvider(this, titleViewModelProvider).get(TitleViewModel::class.java)

        setUpRecyclerView()
        viewModel.articlesByTag.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ResultCallBack.Success -> {

                    response.data.let { articlesByTag ->
                        titleAdapterDiff.differ.submitList(articlesByTag.toList())
                        recyclerTitle.adapter?.notifyDataSetChanged()
                        tagSwipeRefresh.isRefreshing = false

                    }

                }
                is ResultCallBack.Error -> {
                    swipeRefresh.isRefreshing = false

                    Snackbar.make(
                        requireView(),
                        response.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    )
                }
                is ResultCallBack.Loading -> {
                    tagSwipeRefresh.isRefreshing = true

                }


            }

        })

        viewModel.favoriteArticleResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ResultCallBack.Success -> {
                }
                is ResultCallBack.Error -> {
                    Snackbar.make(
                        requireView(),
                        response.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultCallBack.Loading -> {

                }
            }

        })

        viewModel.unFavoriteArticleResponse.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is ResultCallBack.Success -> {


                }
                is ResultCallBack.Error -> {
                    Snackbar.make(
                        requireView(),
                        response.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
                is ResultCallBack.Loading -> {

                }
            }
        })


        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setUpRecyclerView() {
        titleAdapterDiff = ArticleAdapterDiff(this@TitleFragment)
        recyclerTitle.apply {
            setHasFixedSize(true)
            adapter = titleAdapterDiff
            layoutManager = LinearLayoutManager(activity)

        }
    }

    override fun onRefresh() {
        viewModel.getArticlesByTag(viewModel.tag)
    }

    override fun onCardClick(article: ArticleUser, layoutPosition: Int) {
        //go to single article
    }

    override fun onImageClick(author: UserEntity) {
        findNavController().navigate(
            TitleFragmentDirections.actionTitleFragmentToProfileFragment(
                author
            )
        )
    }

    override fun onBookMarkClick(slug: String, isFavorited: Boolean, itemNumber: Int) {

        if (isFavorited) {

            viewModel.articleRepository.getUserNameFromShare().let { userName ->
                viewModel.unFavoritedArticle(
                    slug,
                    itemNumber,
                    userName
                )
            }

        } else {

            viewModel.articleRepository.getUserNameFromShare().let { userName ->
                viewModel.favoriteArticle(
                    slug, itemNumber,
                    userName
                )
            }

        }

    }

}