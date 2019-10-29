package com.airasia.supportwheeloffate.core

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airasia.supportwheeloffate.utils.ActivityUtils
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.utils.extensions.toast
import com.airasia.supportwheeloffate.utils.validationLib.ValidationListener
import com.airasia.supportwheeloffate.utils.validationLib.Validator
import com.airasia.supportwheeloffate.utils.view.dialog.SpotsDialog
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(private val mViewModelClass: Class<VM>) :
    AppCompatActivity(),
    ValidationListener {

    private var mProgressDialogMsg: String? = null
    private var mProgressDialog: AlertDialog? = null
    var validator: Validator? = null

    @LayoutRes
    abstract fun getLayoutRes(): Int

    val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }

    val viewModel by lazy {
        ViewModelProviders.of(this).get(mViewModelClass)
    }

    /**
     * If you want to inject Dependency Injection
     * on your activity, you can override this.
     */
    open fun onInject() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        initViewModel(viewModel)

        super.onCreate(savedInstanceState)

        validator = Validator(binding)
        setupValidator()
        onInject()

        baseObservers()

        mProgressDialogMsg = getString(R.string.please_wait)
        setupProgressDialog()

        initView()

        //track fragment's back stack changed listener
        trackFragmentTransactionState()
    }

    private fun trackFragmentTransactionState() {
        //tracks the fragments pops and push stack
        //we need this to update the toolbar title
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager
                .findFragmentById(R.id.container)
            currentFragment?.onResume()
        }
    }

    private fun baseObservers() {
        viewModel.errorMessage.observe(this, Observer {
            viewModel.toastMessage.postValue(it)
        })

        viewModel.toastMessage.observe(this, Observer {
            toast(it)
        })

        /*viewModel.progressDialogMessage.observe(this, Observer {
            mProgressDialogMsg = it
        })*/

        viewModel.loadingVisibility.observe(this, Observer {
            if (it == View.VISIBLE)
                showProgressDialog()
            else if (it == View.GONE)
                hideProgressDialog()
        })
    }

    open fun initView() {

    }

    /**
     *
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     *
     */
    abstract fun initViewModel(viewModel: VM)

    private fun setupValidator() {
        validator = Validator(binding)
        validator?.apply {
            setValidationListener(this@BaseActivity)
            enableFormValidationMode()
        }
    }

    override fun onValidationError() {}

    override fun onValidationSucceeded() {}

    fun changeFragment(fragment: Fragment, cleanStack: Boolean = false, addToBackStack: Boolean = true) {
        ActivityUtils.changeFragment(this, fragment, cleanStack, addToBackStack)
    }

    fun addFragment(fragment: Fragment, cleanStack: Boolean = false, addToBackStack: Boolean = true) {
        ActivityUtils.addFragment(this, fragment, cleanStack, addToBackStack)
    }

    protected override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    private fun setupProgressDialog() {
        mProgressDialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(mProgressDialogMsg)
            .setCancelable(false)
            .build()
    }

    private fun showProgressDialog() {
        mProgressDialog?.show()
    }

    private fun hideProgressDialog() {
        mProgressDialog?.dismiss()
    }

}