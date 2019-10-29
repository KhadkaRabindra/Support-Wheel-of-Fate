package com.airasia.supportwheeloffate.ui.forgot_password

import androidx.lifecycle.Observer
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.core.BaseFragment
import com.airasia.supportwheeloffate.utils.extensions.makeLabelRequired
import com.airasia.supportwheeloffate.utils.extensions.setSafeOnClickListener
import com.airasia.supportwheeloffate.utils.extensions.toast

class ForgotPasswordFragment : BaseFragment<ForgotPasswordFragmentViewModel, com.airasia.supportwheeloffate.databinding.FragmentForgotPasswordBinding>(ForgotPasswordFragmentViewModel::class.java) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_forgot_password
    }

    override fun initViewModel(viewModel: ForgotPasswordFragmentViewModel) {
        mBinding.viewModel = viewModel
    }

    override fun initView() {
        super.initView()
        listOf(mBinding.newPasswordLabel, mBinding.confirmPasswordLabel).makeLabelRequired()
        mBinding.submitButton.setSafeOnClickListener {
            validator?.toValidate()
        }
        observers()
    }

    private fun observers() {
        viewModel.isPasswordResetSuccess.observe(this, Observer {
            if(it){
                toast("Password reset success.")
                activity?.finish()
            }
        })
    }

    override fun onValidationSucceeded() {
        super.onValidationSucceeded()
        if(viewModel.newPassword.value != viewModel.confirmPassword.value){
            toast("New password and confirm password does not match")
            return
        }
        viewModel.requestForgotPassword()
    }


    companion object{
        fun newInstance() : ForgotPasswordFragment{
            return ForgotPasswordFragment()
        }
    }
}