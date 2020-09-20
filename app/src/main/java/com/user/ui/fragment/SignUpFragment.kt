package com.user.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.article.data.CommentArticleModelEntity
import com.article.data.TagModel
import com.core.db.AppDataBase
import com.example.anull.R
import com.example.anull.databinding.FragmentSignUpBinding
import com.user.data.UserEntity
import com.user.ui.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*

class SignUpFragment : Fragment() {
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding
    private val date: Date = Date()
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDataBase.buildDatabase(requireContext(), MIGRATION_1_2)

//        db.articleDao()
//            .insert(
//                ArticleEntity(
//                    1, 2, "gfdsg", "gsdgs", "gsdgs",
//                    "gfdsgsd", "gdsfgsd", true
//                )
//            )


        db.userDao().insertComment(CommentArticleModelEntity(1, 5, "cj", "chert"))
        db.userDao().insertComment(CommentArticleModelEntity(2, 5, "cj2", "chert2"))

        db.userDao().insertComment(CommentArticleModelEntity(3, 6, "cj", "chert"))
        db.userDao().insertComment(CommentArticleModelEntity(4, 6, "cj2", "chert2"))


        runBlocking {
            delay(2000L)
        }

        //insert tag
        //  insertTag(db)

        //update comment
        //     updateComment(db)

        //     searchArticle(db)
//
//        val tempList2=db.userDao().getUserWithArticleAndCommentAndTag()
//        tempList2.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//              Log.d("sfatehi", " list of user and sub : ${it}")
////
//
//        })

        //delete Abshari

        //   deleteAbshari(db)


        //   getUserAndCountOfArticle(db)

        // getSizeOfArticles(db)
//
//        val tempList3=db.userDao().getUserAndArticleCount()
//        tempList3.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            Log.d("sfatehi", "name of user: ${it.get(0).user.name}  countOfArticle: ${it.get(0).articleCount} ")
//        })


//        Log.d("sfatehi","titi : ${db.userDao().getUserAndArticleCount()}")

        //   getSizaOfTags(db)


        // getSizeOfComments(db)


        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding.lifecycleOwner = this
        binding.signUViewModel = viewModel

        binding.tvGoLoginScreen.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }

        tvMemberShip.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToTitleFragment())
        }


        singUpButton.setOnClickListener {

            //below line is for app bar layout that don,t go behind the status bar

            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            requireActivity().window.statusBarColor = Color.parseColor("#813ac1")


        }


    }

    private fun getSizeOfComments(db: AppDataBase) {
        val tempComment = db.userDao().getAllComments()
        tempComment.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("sfatehi", " count of comments ${it.size}")
        })
    }

    private fun getSizaOfTags(db: AppDataBase) {
        val tempTag = db.userDao().getAllTags()
        tempTag.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("sfatehi", "count of tags ${it.size}")
        })
    }



}