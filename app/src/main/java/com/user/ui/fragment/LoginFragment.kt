package com.user.ui.fragment


import android.content.Context
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.config.FunctionHelper
import com.config.MyApp
import com.core.db.AppDataBase
import com.example.anull.R
import com.example.anull.databinding.FragmentLoginBinding
import com.user.data.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener {

    var username: String = ""
    var password: String = ""
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(requireContext(), AppDataBase::class.java, "dp")
            .allowMainThreadQueries().build()

        val userLocalDataSource = UserLocalDataSource(
            requireActivity().getSharedPreferences(
                MyApp.NAME_PREF,
                Context.MODE_PRIVATE
            ), db.userDao()
        )

        val userRemoteDataSource = UserRemoteDataSource()

        loginViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginViewModel(
                    repo = UserRepo(
                        userLocalDataSource,
                        userRemoteDataSource
                    )
                ) as T
            }

        }).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvGoToBeMember.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                if (checkData()) {
//                    loginViewModel.loginIsPossible(username, password) -> {
//                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
//                        requireActivity().window.decorView.systemUiVisibility =
//                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        requireActivity().window.statusBarColor = Color.parseColor("#813ac1")
//
//                    }
                    loginViewModel.login(
                        LoginReq(
                            binding.edtUser.text.toString(),
                            binding.edtPass.text.toString()
                        ),
                        ::callBackLogin
                    )
                }
            }
            R.id.tv_go_to_be_member -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }
        }
    }

    fun callBackLogin(boolean: Boolean, message: String?) {
        if (boolean) {
            Toast.makeText(requireContext(), "ok", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), message.orEmpty(), Toast.LENGTH_LONG).show()
        }
    }

    private fun checkData(): Boolean {
        username = edt_user.text.toString()
        password = edt_pass.text.toString()

        when {
            username.trim().isEmpty() -> {
                edt_user_inputLayout.error = "خطا! این کادر را پر کنید"
                return false
            }
            password.trim().isEmpty() -> {
                passLoginInputLayout.error = "خطا! این کادر را پر کنید"
                return false
            }
            else -> return true
        }
    }
}