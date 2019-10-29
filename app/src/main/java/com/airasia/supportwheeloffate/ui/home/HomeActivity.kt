package com.airasia.supportwheeloffate.ui.home

import android.content.Context
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.airasia.supportwheeloffate.core.BaseActivity
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.ToolbarCommonActivity
import com.airasia.supportwheeloffate.data.listners.NavigationDrawerCallbacks
import com.airasia.supportwheeloffate.data.local.ViewType
import com.airasia.supportwheeloffate.databinding.ActivityHomeBinding
import com.airasia.supportwheeloffate.repository.UserRepository
import com.airasia.supportwheeloffate.ui.nav.NavigationDrawerFragment
import com.airasia.supportwheeloffate.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_home.*
import kotlinx.android.synthetic.main.toolbar_home.view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class HomeActivity : BaseActivity<HomeActivityViewModel, ActivityHomeBinding>(
    HomeActivityViewModel::class.java
), NavigationDrawerCallbacks {

    @Inject
    lateinit var userRepository: UserRepository

    override fun getLayoutRes() = R.layout.activity_home

    override fun initViewModel(viewModel: HomeActivityViewModel) {
        binding.viewModel = viewModel
    }

    override fun initView() {
        super.initView()
        (application as? RemitApplication)?.component?.inject(this)
        changeFragment(HomeFragment.newInstance(), addToBackStack = false)

        binding.customToolbar.navIconImageVIew.setSafeOnClickListener {
            openDrawer()
        }
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        setupNavigationDrawerView()
    }


    /**
     * intialize navigation drawer layout and fragment
     */
    private fun setupNavigationDrawerView() {
        val frag = NavigationDrawerFragment()
        val ft = supportFragmentManager?.beginTransaction()
        ft?.replace(R.id.fragment_drawer, frag)
        ft?.commitAllowingStateLoss()
        closeDrawer()
    }

    /**
     * opens drawer layout
     */
    private fun openDrawer() {
        if (!drawerLayout.isDrawerOpen(fragment_drawer))
            drawerLayout.openDrawer(fragment_drawer)
    }

    /**
     * closes drawer layout
     */
    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(fragment_drawer))
            drawerLayout.closeDrawer(fragment_drawer)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_drawer -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationDrawerItemSelected(position: Int?) {
        when (position) {
            NavigationDrawerFragment.NavigationIds.MyAccount -> openNavigateBackBaseActivity(ViewType.MY_ACCOUNT)
            NavigationDrawerFragment.NavigationIds.PrefundHistory -> openNavigateBackBaseActivity(ViewType.PREFUND_HISTORY)
            NavigationDrawerFragment.NavigationIds.CashInHostory -> openNavigateBackBaseActivity(ViewType.CASH_IN_HISTORY)
            NavigationDrawerFragment.NavigationIds.Statements -> openNavigateBackBaseActivity(ViewType.STATEMENTS)
            NavigationDrawerFragment.NavigationIds.Settings -> openNavigateBackBaseActivity(ViewType.SETTINGS)
            NavigationDrawerFragment.NavigationIds.ContactUs -> openNavigateBackBaseActivity(ViewType.CONTACT_US)
            NavigationDrawerFragment.NavigationIds.Logout -> {
                userRepository.doLogout(this)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun openNavigateBackBaseActivity(viewType: ViewType) {
        when(viewType){
            ViewType.MY_ACCOUNT -> startActivity<ToolbarCommonActivity>(ToolbarCommonActivity.INTENT_EXTRA_VIEW_TYPE to ViewType.MY_ACCOUNT)
            ViewType.PREFUND_HISTORY -> startActivity<ToolbarCommonActivity>(ToolbarCommonActivity.INTENT_EXTRA_VIEW_TYPE to ViewType.PREFUND_HISTORY)
            ViewType.STATEMENTS -> startActivity<ToolbarCommonActivity>(ToolbarCommonActivity.INTENT_EXTRA_VIEW_TYPE to ViewType.STATEMENTS)
            else -> {

            }
        }
    }


    companion object {
        val IGNORE_PIN = "IGNORE_PIN"
        fun start(context: Context?) {
            context?.startActivity<HomeActivity>()
        }
    }
}
