package com.article.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.article.data.ArticleRepository
import com.article.data.modelfromservice.ArticleInCreateArticleModel
import com.article.data.modelfromservice.CreateArticleModel
import com.article.ui.viewmodel.WriteArticleViewModel
import com.article.ui.viewmodel.providerfactory.WriteViewModelProviderFactory
import com.core.db.AppDataBase
import com.example.anull.R
import com.example.anull.databinding.FragmentWriteArticleBinding
import com.user.data.modelfromservice.BodyOfEditedArticle
import com.user.data.modelfromservice.EditArticleRequest
import kotlinx.android.synthetic.main.fragment_write_article.*


class WriteArticleFragment : Fragment() {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE articles ADD COLUMN test TEXT")
        }
    }
    var args: Bundle = Bundle()
    private lateinit var writeViewModel: WriteArticleViewModel
    private lateinit var binding: FragmentWriteArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        val articleRepository =
            ArticleRepository()
        val writeViewModelProvider = WriteViewModelProviderFactory(articleRepository)
        writeViewModel =
            ViewModelProvider(this, writeViewModelProvider).get(WriteArticleViewModel::class.java)

        writeViewModel.argsFromProf = args
        writeViewModel.checkArgsIsEmpty(args)


        // writeViewModel.checkArgsIsNull(args)
        writeViewModel.activity = requireActivity()

        binding.writeViewModel = writeViewModel
        binding.lifecycleOwner = this

        writeViewModel.isFromEdit.observe(viewLifecycleOwner, Observer { isFromEdit ->
            if (isFromEdit) {

                submit_article.setOnClickListener {

                    //show progress bar
                    showProgressBar()

                    val body = edit_text.text.toString().trim()
                    val slug = writeViewModel.article.slug

                    writeViewModel.updateArticle(slug, EditArticleRequest(BodyOfEditedArticle(body)))
                    writeViewModel.editedArticle

                }
            }
            else {
                binding.submitArticle.setOnClickListener {
                    var tags = writeViewModel.tagsChip.values.toList<String>()
                    val createArticleModel = CreateArticleModel(
                        ArticleInCreateArticleModel(
                            description = "Nothing",
                            body = edit_text.text.toString(),
                            title = edit_title.text.toString(),
                            tagList = tags
                        )
                    )
                    writeViewModel.createArticle(createArticleModel)
                }
            }
        })

        writeViewModel.isUpdated.observe(viewLifecycleOwner, Observer { isUpdatedSuccess ->
            if (isUpdatedSuccess) {
                hideProgress()
                val bundle = Bundle()

                bundle.putString("userName", writeViewModel.userName)
                findNavController().navigate(
                    R.id.action_writeArticleFragment_to_profileFragment,
                    bundle
                )
                writeViewModel.isUpdated.value = false
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

    private fun showProgressBar() {
        writeProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        writeProgress.visibility = View.INVISIBLE
    }

}

