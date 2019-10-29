package com.airasia.supportwheeloffate.core

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airasia.supportwheeloffate.utils.ActivityUtils
import com.airasia.supportwheeloffate.utils.DialogHelper
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.utils.extensions.toast
import com.airasia.supportwheeloffate.utils.validationLib.ValidationListener
import com.airasia.supportwheeloffate.utils.validationLib.Validator
import com.airasia.supportwheeloffate.utils.view.dialog.CustomProgressBarDialog

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(private val mViewModelClass: Class<VM>) :
    androidx.fragment.app.Fragment(),
    ValidationListener {
    lateinit var viewModel: VM
    open lateinit var mBinding: DB
    lateinit var mContext: Context
    var validator: Validator? = null

    private var mProgressDialogMsg: String? = null
    private var mProgressDialog: AlertDialog? = null

    private fun init(inflater: LayoutInflater, container: ViewGroup) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        validator = Validator(mBinding)

        mBinding.setLifecycleOwner(this)
    }

    open fun init() {}

    @LayoutRes
    abstract fun getLayoutRes(): Int

    private fun getViewM(): VM = ViewModelProviders.of(this).get(mViewModelClass)

    open fun onInject() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewM()

    }

    abstract fun initViewModel(viewModel: VM)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mContext = activity!!
        init(inflater, container!!)
        init()
        initViewModel(viewModel)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupValidator()
        viewModel = getViewM()
        setHasOptionsMenu(false)
        mBinding.setVariable(com.airasia.supportwheeloffate.BR.viewModel, viewModel)
        mBinding?.executePendingBindings()
        baseObservers()
        mProgressDialogMsg = getString(R.string.please_wait)
        viewStarter()
    }

    open fun viewStarter() {
        initView()
    }


    private fun setupValidator() {
        validator = Validator(mBinding)
        validator?.apply {
            setValidationListener(this@BaseFragment)
            enableFormValidationMode()
        }
    }

    override fun setUserVisibleHint(visible: Boolean) {
        super.setUserVisibleHint(visible)
        if (visible && isResumed) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!userVisibleHint) {
            return
        }

        onFragmentVisible()
        //INSERT CUSTOM CODE HERE
    }

    open fun onFragmentVisible() {

    }

    open fun initView() {}

    open fun refresh() {}


    private fun baseObservers() {
        viewModel.errorMessage.observe(this, Observer {
            viewModel.toastMessage.postValue(it)
        })

        viewModel.toastMessage.observe(this, Observer {
            toast(it)
        })

        viewModel.progressDialogMessage.observe(this, Observer {
            mProgressDialogMsg = it
        })

        viewModel.loadingVisibility.observe(this, Observer {
            if (it == View.VISIBLE)
                showProgressDialog()
            else if (it == View.GONE)
                hideProgressDialog()
        })
        viewModel.errorDialog.observe(this, Observer {
            showErrorMessageDialog(it)
        })
    }

    private fun showErrorMessageDialog(message: String?) {
        DialogHelper.showDialog(
            context = mContext,
            title = "",
            message = message.toString(),
            positiveBtnText = "Ok",
            negativeBtnText = "",
            positiveBtnClickListener = DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() },
            negativeBtnClickListener = DialogInterface.OnClickListener { dialog, which -> })
    }

    fun changeFragment(fragment: Fragment, cleanStack: Boolean = false, addToBackStack: Boolean = true) {
        ActivityUtils.changeFragment(activity as AppCompatActivity, fragment, cleanStack, addToBackStack)
    }

    fun addFragment(fragment: Fragment, cleanStack: Boolean = false, addToBackStack: Boolean = true) {
        ActivityUtils.addFragment(activity as AppCompatActivity, fragment, cleanStack, addToBackStack)
    }

    fun finish() {
        if (activity != null && activity?.isFinishing == false) {
            activity?.finish()
        }
    }

    private fun updateProgressDialogMessage(message: String) {
        mProgressDialog?.setMessage(message)
    }

    private fun showProgressDialog() {
        if (activity != null && isAdded) {
            CustomProgressBarDialog.progressDialog = CustomProgressBarDialog.showProgressDialog(activity)
            CustomProgressBarDialog.progressDialog.show()
        }
    }

    private fun hideProgressDialog() {
        if (CustomProgressBarDialog.progressDialog != null && CustomProgressBarDialog.progressDialog.isShowing)
            CustomProgressBarDialog.progressDialog.dismiss()
    }

    override fun onValidationSucceeded() {}

    override fun onValidationError() {
//        viewModel.toastMessage.value = "validation failed"
    }

}
