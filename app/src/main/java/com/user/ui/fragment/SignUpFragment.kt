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
import com.core.db.AppDataBase
import com.example.anull.R
import com.example.anull.databinding.FragmentSignUpBinding
import com.user.data.UserRepository
import com.user.data.localdatasource.UserLocalDataSource
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.modelfromservice.User
import com.user.data.reomtedatasource.UserRemote
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




        val userLocalDataSource=
            UserLocalDataSource(AppDataBase.invoke(requireContext(),MIGRATION_1_2))
        val userRemoteDataSource= UserRemote()
        val userRepository = UserRepository(userLocalDataSource,userRemoteDataSource)
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