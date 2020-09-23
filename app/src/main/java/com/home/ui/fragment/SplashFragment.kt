package com.home.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.core.db.AppDataBase
import com.example.anull.R
import com.example.anull.databinding.FragmentSplashBinding
import com.home.data.SplashViewModel
import com.storage.data.PreferenceProperty
import com.user.data.UserLocalDataSource
import com.user.data.UserRemoteDataSource
import com.user.data.UserRepo

@Suppress("UNCHECKED_CAST")
class SplashFragment : Fragment() {
    private val splashTimeOut: Long = 3000
    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewmodel: SplashViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(requireContext(), AppDataBase::class.java, "dp")
            .allowMainThreadQueries().build()


        val userLocalDataSource = UserLocalDataSource(
            requireActivity().getSharedPreferences(
                PreferenceProperty.APP_PREF_NAME,
                Context.MODE_PRIVATE
            ), db.userDao()
        )

        val userRemoteDataSource = UserRemoteDataSource()

        viewmodel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SplashViewModel(
                    userRepo = UserRepo(
                        userLocalDataSource,
                        userRemoteDataSource
                    )
                ) as T
            }
        }).get(
            SplashViewModel::class.java
        )

    }

    private fun changeTopOfScreen(sw: Int) {
        viewmodel.validUser()

        viewmodel.isValid.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        })

//        if (sw == 0) {
//            val reqWin = requireActivity().window
//            reqWin.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            reqWin.statusBarColor = Color.TRANSPARENT
//
//        } else if (sw == 1) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            requireActivity().window.statusBarColor = Color.parseColor("#813ac1")
//        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val window = requireActivity().window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        changeTopOfScreen(0)

    }
}