package com.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.anull.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener {

    var username: String = ""
    var password: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.window?.attributes?.softInputMode
        tv_go_to_be_member.setOnClickListener(this)
        btn_login.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                checkData()
            }
            R.id.tv_go_to_be_member -> {
                val signUpFragment = SignUpFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.splashFrame, signUpFragment).commit()
            }
        }
    }

    private fun checkData() {
        username = edt_user.text.toString()
        if (!username.trim().isNotEmpty()) {
            Toast.makeText(context, getString(R.string.emptyfields), Toast.LENGTH_SHORT).show()
        }

        password = pass.text.toString()
        if (!password.trim().isNotEmpty()) {
            Toast.makeText(context, getString(R.string.emptyfields), Toast.LENGTH_SHORT).show()
        }
    }
}