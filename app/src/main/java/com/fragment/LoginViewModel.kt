package com.fragment

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {


    fun loginIsPossible(email: String, password: String): Boolean {
        //search in database and find email and password

        return true
    }
}