package com.airasia.supportwheeloffate.ui.nav

import android.content.Context
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.core.BaseFragment
import com.airasia.supportwheeloffate.data.listners.NavigationDrawerCallbacks
import com.airasia.supportwheeloffate.data.local.NavigationItem
import com.airasia.supportwheeloffate.ui.nav.adapter.NavigationDrawerListAdapter
import com.airasia.supportwheeloffate.utils.extensions.logD
import com.airasia.supportwheeloffate.utils.extensions.setSafeOnClickListener
import java.lang.Exception

class NavigationDrawerFragment: BaseFragment<NavigationDrawerFragmentViewModel,
        com.airasia.supportwheeloffate.databinding.FragmentNavigationDrawerBinding>(NavigationDrawerFragmentViewModel::class.java) {
    override fun getLayoutRes() = R.layout.fragment_navigation_drawer

    override fun initViewModel(viewModel: NavigationDrawerFragmentViewModel) {
        mBinding.viewModel = viewModel
    }

    private var mCallbacks: NavigationDrawerCallbacks? = null

    override fun initView() {
        super.initView()

        mBinding.logoutButton.setSafeOnClickListener {
            mCallbacks!!.onNavigationDrawerItemSelected(NavigationIds.Logout)
        }
        viewModel.setNavigationList(menus)
        viewModel.setFullNameAndUserName()
        mBinding.recyclerView.adapter = NavigationDrawerListAdapter{
            mCallbacks!!.onNavigationDrawerItemSelected(it.id)
        }
    }


    private val menus: ArrayList<NavigationItem> get() {
        val items = ArrayList<NavigationItem>()
        items.add(NavigationItem(NavigationIds.MyAccount, getString(R.string.home), R.drawable.ic_file_download_black_24dp))
        items.add(NavigationItem(NavigationIds.PrefundHistory, getString(R.string.home), R.drawable.ic_file_download_black_24dp))
        items.add(NavigationItem(NavigationIds.CashInHostory, getString(R.string.home), R.drawable.ic_file_download_black_24dp))
        items.add(NavigationItem(NavigationIds.Statements, getString(R.string.home), R.drawable.ic_file_download_black_24dp))
        items.add(NavigationItem(NavigationIds.Settings, getString(R.string.home), R.drawable.ic_file_download_black_24dp))
        items.add(NavigationItem(NavigationIds.ContactUs, getString(R.string.home), R.drawable.ic_file_download_black_24dp))
        return items
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
        try {
            mCallbacks = mContext as NavigationDrawerCallbacks?
        } catch (ex: Exception) {
            logD("error here : ${ex.localizedMessage}")
            ex.printStackTrace()
        }
    }

    object NavigationIds {
        const val MyAccount: Int = 0
        const val PrefundHistory: Int = 1
        const val CashInHostory: Int = 2
        const val Statements: Int = 3
        const val Settings: Int = 4
        const val ContactUs: Int = 5
        const val Logout: Int = 6
    }
}