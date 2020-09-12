package com.user.ui.viewmodel.providerfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.user.data.UserRepository
import com.user.ui.viewmodel.ProfileViewModel

class ProfileViewModelProviderFactory( val userRepository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(userRepository) as T
    }
}