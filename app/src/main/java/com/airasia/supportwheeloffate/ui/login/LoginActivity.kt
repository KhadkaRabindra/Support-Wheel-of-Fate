package com.airasia.supportwheeloffate.ui.login

import androidx.lifecycle.Observer
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.core.BaseActivity
import com.airasia.supportwheeloffate.core.ToolbarCommonActivity
import com.airasia.supportwheeloffate.data.local.ViewType
import com.airasia.supportwheeloffate.ui.home.HomeActivity
import com.airasia.supportwheeloffate.utils.extensions.makeLabelRequired
import com.airasia.supportwheeloffate.utils.extensions.setSafeOnClickListener
import com.airasia.supportwheeloffate.utils.getDeviceUniqueId

class LoginActivity :
    BaseActivity<LoginActivityViewModel, com.airasia.supportwheeloffate.databinding.ActivityLoginBinding>(
        LoginActivityViewModel::class.java
    ) {
    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun initViewModel(viewModel: LoginActivityViewModel) {
        binding.viewModel = viewModel
    }

    override fun initView() {
        super.initView()

        checkLoginStatus()
        listOf(binding.emailLabel, binding.passwordLabel).makeLabelRequired()

        binding.agentButton.setSafeOnClickListener {
            openRegistrationActivity()
        }

        binding.loginButton.setSafeOnClickListener {
            validator?.toValidate()
        }
        binding.forgotPasswordTextView.setSafeOnClickListener {
            ToolbarCommonActivity.start(this, ViewType.FORGOT_PASSWORD)
        }

        observers()
    }

    private fun observers() {
        viewModel.loginSuccess.observe(this, Observer {
            if(it)
                openHomeActivity()
        })
    }

    override fun onValidationSucceeded() {
        super.onValidationSucceeded()
        viewModel.requestLogin(getDeviceUniqueId(this))
    }

    private fun checkLoginStatus() {
        if (viewModel.getLoginStatus() == true)
            openHomeActivity()
    }

    private fun openHomeActivity() {
        HomeActivity.start(this)
        finish()
    }

    private fun openRegistrationActivity() {
        ToolbarCommonActivity.start(this, ViewType.REGISTRATION)
    }
}