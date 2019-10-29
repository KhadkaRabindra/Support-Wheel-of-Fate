package com.airasia.supportwheeloffate.ui.register

import androidx.lifecycle.Observer
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.core.BaseFragment
import com.airasia.supportwheeloffate.core.ToolbarCommonActivity
import com.airasia.supportwheeloffate.utils.extensions.makeLabelRequired
import com.airasia.supportwheeloffate.utils.extensions.setSafeOnClickListener
import com.airasia.supportwheeloffate.utils.extensions.toast

class RegistrationFragment :
    BaseFragment<RegistrationFragmentViewModel, com.airasia.supportwheeloffate.databinding.FragmentRegistrationBinding>(
        RegistrationFragmentViewModel::class.java
    ) {
    override fun initViewModel(viewModel: RegistrationFragmentViewModel) {
        mBinding.viewModel = viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_registration
    }

    override fun onFragmentVisible() {
        super.onFragmentVisible()
        if (activity is ToolbarCommonActivity)
            (activity as ToolbarCommonActivity).setToolbarTitle(getString(R.string.registration))
    }

    override fun initView() {
        super.initView()

        listOf(
            mBinding.nameLabel,
            mBinding.mobileNumberLabel,
            mBinding.emailLabel,
            mBinding.passwordLabel,
            mBinding.confirmPasswordLabel
        ).makeLabelRequired()
        mBinding.submitButton.setSafeOnClickListener {
            validator?.toValidate()
        }
        observers()
    }

    private fun observers() {
        viewModel.isRegistrationSucces.observe(this, Observer {
            if (it)
                openMessageActivity()
        })
    }

    override fun onValidationSucceeded() {
        super.onValidationSucceeded()
        if (viewModel.password.value != viewModel.confirmPassword.value) {
            toast("Password and confirm password does not match")
        } else {
            viewModel.requestRegistrationSubmit()
        }

    }

    private fun openMessageActivity() {
        /*MessageActivity.start(context)
        activity?.finish()*/
        activity?.finish()
    }


    companion object {
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }
}