package com.airasia.supportwheeloffate.core

import android.content.Context
import com.airasia.supportwheeloffate.data.local.ViewType
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.databinding.ActivityToolbarCommonBinding
import com.airasia.supportwheeloffate.ui.forgot_password.ForgotPasswordFragment
import com.airasia.supportwheeloffate.ui.register.RegistrationFragment
import com.airasia.supportwheeloffate.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_toolbar_common.*
import kotlinx.android.synthetic.main.activity_toolbar_common.view.*
import org.jetbrains.anko.startActivity

class ToolbarCommonActivity : BaseActivity<ToolbarCommonActivityViewModel, ActivityToolbarCommonBinding>(
    ToolbarCommonActivityViewModel::class.java) {

    private var mViewType: ViewType? = null

    override fun getLayoutRes() = R.layout.activity_toolbar_common

    override fun initViewModel(viewModel: ToolbarCommonActivityViewModel) {
        binding.viewModel = viewModel
    }

    override fun initView() {
        super.initView()
        setupToolbar()
        binding.backIcon.setSafeOnClickListener {
            onBackPressed()
        }
        handleRouter(intent.getSerializableExtra(INTENT_EXTRA_VIEW_TYPE) as ViewType?)
    }

    private fun handleRouter(viewType: ViewType?) {
        when (viewType) {
            ViewType.REGISTRATION -> changeFragment(RegistrationFragment.newInstance(), addToBackStack = false)
            ViewType.FORGOT_PASSWORD -> changeFragment(ForgotPasswordFragment.newInstance(), addToBackStack = false)
            /*ViewType.RESET_PASSWORD -> changeFragment(ResetPasswordFragment.newInstance(), addToBackStack = false)
            ViewType.TRANSACTION_DETAIL -> changeFragment(
                TrasactionDetailFragment.newInstance(
                    intent.getParcelableExtra(
                        INTENT_EXTRA_TRANSACTION_DETAIL
                    ) as InitTransactionDetailResponse?
                ), addToBackStack = false
            )
            ViewType.MY_ACCOUNT -> changeFragment(MyAccountFragment.newInstance(), addToBackStack = false)
            ViewType.PREFUND -> changeFragment(PrefundFragment.newInstance(), addToBackStack = false)
            ViewType.PREFUND_HISTORY -> changeFragment(PrefundHistoryFragment.newInstance(), addToBackStack = false)
            ViewType.STATEMENTS -> changeFragment(StatementsFragment.newInstance(), addToBackStack = false)
            ViewType.BANK_LIST -> changeFragment(BanksFragment.newInstance(), addToBackStack = false)*/
        }
    }

    fun setToolbarTitle(title: String) {
        toolBar.toolbarTitle.text = title
    }


    private fun setupToolbar() {
        setSupportActionBar(toolBar)
    }

    companion object {

        const val INTENT_EXTRA_VIEW_TYPE = "intent_extra_view_type"
        const val INTENT_EXTRA_TRANSACTION_DETAIL = "intent_extra_transaction_detail"

        fun start(context: Context?, viewType: ViewType) {
            context?.startActivity<ToolbarCommonActivity>(INTENT_EXTRA_VIEW_TYPE to viewType)
        }

    }
}