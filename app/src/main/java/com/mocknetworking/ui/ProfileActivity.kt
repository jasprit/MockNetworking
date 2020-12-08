package com.mocknetworking.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mocknetworking.R
import com.mocknetworking.base.BaseActivity
import com.mocknetworking.databinding.ProfileActivityBinding
import com.mocknetworking.model.LoginDataService


class ProfileActivity : BaseActivity() {

    private var binding: ProfileActivityBinding? = null
    private val viewModel by lazy { ViewModelProviders.of(this)[UserViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.profile_activity)
        viewModel.setUserName(LoginDataService.loginData?.username ?: "")
        binding?.profile?.text = viewModel.getUserName()

    }
}