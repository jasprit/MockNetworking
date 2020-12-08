package com.mocknetworking

import android.content.Intent
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.mocknetworking.base.ApiResponseListener
import com.mocknetworking.base.BaseActivity
import com.mocknetworking.databinding.ActivityMainBinding
import com.mocknetworking.model.LoginData
import com.mocknetworking.model.LoginDataService

import com.mocknetworking.ui.ProfileActivity
import com.mocknetworking.ui.UserViewModel
import com.mocknetworking.util.isStringHasNumber

import com.mocknetworking.util.displayToast
import com.mocknetworking.util.isNetworkConnected

class MainActivity : BaseActivity(), ApiResponseListener {

    private var binding: ActivityMainBinding? = null
    private val viewModel by lazy { ViewModelProviders.of(this)[UserViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding?.login?.setOnClickListener {
            if (isValidInput() && isNetworkConnected())
                requestForLogin()
        }
        getCallbacks(viewModel = viewModel, this)
    }

    override fun <T> onResponse(it: T) {
        LoginDataService.loginData = it as? LoginData
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    override fun onError(it: Throwable) {
        displayToast(this, it.toString())
    }

    private fun requestForLogin() {
        val username = binding?.username?.text?.toString()?.trim()
        val password = binding?.password?.text?.toString()?.trim()
        viewModel.getLoginData(username, password)
    }

    private fun isValidInput(): Boolean {
        return when {
            binding?.username?.text.toString().trim().isEmpty() -> {
                displayToast(this, getString(R.string.error_username))
                false
            }

            !binding?.username?.text.toString().isStringHasNumber() -> {
                displayToast(this, getString(R.string.username_digit_error))
                false
            }

            binding?.password?.text.toString().trim().isEmpty() -> {
                displayToast(this, getString(R.string.error_password))
                false
            }

            !binding?.password?.text.toString().isStringHasNumber() -> {
                displayToast(this, getString(R.string.password_digit_error))
                false
            }

            else -> true
        }
    }
}