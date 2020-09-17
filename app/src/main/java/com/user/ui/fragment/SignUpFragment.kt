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
import com.article.data.TagEntity
import com.core.db.AppDataBase
import com.example.anull.R
import com.example.anull.databinding.FragmentSignUpBinding
import com.user.data.ArticleEntity
import com.user.data.UserEntity
import com.user.data.UserRepository
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.User
import com.user.ui.viewmodel.SignUpViewModel
import com.user.ui.viewmodel.providerfactory.SiguUpViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
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
        db.userDao()
            .insertUser(UserEntity(1, "goh", "koonkesh0", "www.porn.com", 3241, "hot babe"))

//        db.articleDao()
//            .insert(
//                ArticleEntity(
//                    1, 2, "gfdsg", "gsdgs", "gsdgs",
//                    "gfdsgsd", "gdsfgsd", true
//                )
//            )
        db.articleDao().clear()
        db.userDao().clear()

        db.userDao().insertUser(UserEntity(3, "sajjad", "none", "ss", 300, "sfvbbbb"))
        db.userDao().insertUser(UserEntity(43, "cjj", "nosne", "sss", 30, "sfbbb"))

        db.articleDao().insert(ArticleEntity(5, 3, date, "dsb", "dg", "dsg", "sdg", true))
        db.articleDao().insert(ArticleEntity(6, 3, date, "dsb", "dg", "dsg", "sdg", true))

        db.articleDao().insert(ArticleEntity(71, 43, date, "hj", "dg", "dsdddg", "sdhhhg", false))

        db.userDao().insertComment(CommentArticleModelEntity(1, 5, "cj", "chert"))
        db.userDao().insertComment(CommentArticleModelEntity(2, 5, "cj2", "chert2"))

        db.userDao().insertComment(CommentArticleModelEntity(3, 6, "cj", "chert"))
        db.userDao().insertComment(CommentArticleModelEntity(4, 6, "cj2", "chert2"))


        db.userDao().insertTag(TagEntity(43, 5, "nothing"))
        db.userDao().insertTag(TagEntity(44, 5, "nothing"))

        db.userDao().insertTag(TagEntity(45, 6, "nothing"))
        db.userDao().insertTag(TagEntity(46, 6, "nothing"))




        getArticleAndCommentAndTag(db)

        getUserWithArticleAndCommentAndTag(db)

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


        val userRepository = UserRepository(AppDataBase.invoke(requireContext(), MIGRATION_1_2))
        val singUpViewModelProvider = SiguUpViewModelProviderFactory(userRepository)
        viewModel =
            ViewModelProvider(this, singUpViewModelProvider).get(SignUpViewModel::class.java)
        binding.lifecycleOwner = this
        binding.signUViewModel = viewModel

        binding.tvGoLoginScreen.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }

        tvMemberShip.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToTitleFragment())
        }

        viewModel.isRegisterSuccess.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { isRegisterSuccess ->
                if (isRegisterSuccess) {
                    hideProgressBar()
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())

                }

            })

        singUpButton.setOnClickListener {
            if (!checkEditTextIsEmpty()) {

                showProgressBar()
                viewModel.register(
                    RegisterRequest(
                        User(
                            email = emailEditText.text.toString(),
                            username = userNameEditText.text.toString(),
                            password = passSignUp.text.toString()
                        )
                    )
                )
//            //below line is for app bar layout that don,t go behind the status bar

                // hideProgressBar()

                requireActivity().window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                requireActivity().window.statusBarColor = Color.parseColor("#813ac1")


            }


        }


    }

    private fun getSizeOfComments(db: AppDataBase) {
        val tempComment = db.userDao().getAllComments()
        tempComment.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("sfatehi", " count of comments ${it.size} \n")
        })
    }

    private fun getSizaOfTags(db: AppDataBase) {
        val tempTag = db.userDao().getAllTags()
        tempTag.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("sfatehi", "count of tags ${it.size} \n")
        })
    }

    private fun getSizeOfArticles(db: AppDataBase) {
        val tempList = db.articleDao().getAllArticles()
        tempList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("sfatehi", "count of articles ${it.size} \n")
        })
    }

    private fun getUserAndCountOfArticle(db: AppDataBase) {
        val tempUserAndCountOfArticle = db.userDao().getUserAndCountOfArticle()
        tempUserAndCountOfArticle.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("sfatehi", "user and count of his article : ${it}\n")
        })
    }

    private fun deleteAbshari(db: AppDataBase) {
        db.userDao().deleteUsers(UserEntity(3, "sajjad", "none", "ss", 300, "sfvbbbb"))
    }

    private fun searchArticle(db: AppDataBase) {
        Log.d("sfatehi", "result of search : ${db.articleDao().searchArticleWithCommentAndTag(71)}")
    }

    private fun updateComment(db: AppDataBase) {
        db.userDao().updateComments(CommentArticleModelEntity(3, 6, "cj", "update comment"))
    }

    private fun insertTag(db: AppDataBase) {
        db.userDao().insertTag(TagEntity(6, 71, "new tag"))
    }

    private fun getUserWithArticleAndCommentAndTag(db: AppDataBase) {
        val tempUserWithArticleAndCommentAndTag = db.userDao().getUserWithArticleAndCommentAndTag()
        tempUserWithArticleAndCommentAndTag.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                Log.d("sfatehi", "${it}")
            })
    }

    private fun getArticleAndCommentAndTag(db: AppDataBase) {
        val tempArticleAndcommentAndTag = db.articleDao().getArticleAndCommentAndTag()
        tempArticleAndcommentAndTag.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("sfatehi", "ok: tit:  ${it}")

        })
    }


    private fun hideProgressBar() {
        registerProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        registerProgressBar.visibility = View.VISIBLE

    }

    private fun checkEditTextIsEmpty(): Boolean {

        val userName = userNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val pass = passSignUp.text.toString().trim()
        val repetitionPass = repetitionPass.text.toString().trim()
        when {
            userName.isEmpty() -> {
                userNameInputLayout.error = "خطا! این کادر نباید خالی باشد "
                return true
            }
            email.isEmpty() -> {
                emailInputLayout.error = "خطا! این کادر نباید خالی باشد "
                return true
            }
            pass.isEmpty() -> {
                passLoginInputLayout.error = "خطا! این کادر نباید خالی باشد "
                return true
            }
            repetitionPass.isEmpty() -> {
                repetitionPassInputLayout.error = "خطا! این کادر نباید خالی باشد "
                return true
            }
            else -> return false
        }

    }

}