package com.airasia.supportwheeloffate.di.component

import android.content.Context
import android.content.SharedPreferences
import com.airasia.supportwheeloffate.di.module.ApplicationModule
import com.airasia.supportwheeloffate.di.module.NetModule
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.ToolbarCommonActivityViewModel
import com.airasia.supportwheeloffate.ui.forgot_password.ForgotPasswordFragmentViewModel
import com.airasia.supportwheeloffate.ui.home.HomeActivity
import com.airasia.supportwheeloffate.ui.home.HomeActivityViewModel
import com.airasia.supportwheeloffate.ui.home.HomeFragmentViewModel
import com.airasia.supportwheeloffate.ui.login.LoginActivityViewModel
import com.airasia.supportwheeloffate.ui.nav.NavigationDrawerFragmentViewModel
import com.airasia.supportwheeloffate.ui.nav.adapter.NavigationDrawerItemViewModel
import com.airasia.supportwheeloffate.ui.register.RegistrationFragmentViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton

@Component(modules = arrayOf(ApplicationModule::class, NetModule::class))


interface ApplicationComponent {
    fun app(): RemitApplication


    fun context(): Context

    fun preferences(): SharedPreferences

    fun inject(target: ToolbarCommonActivityViewModel)

    fun inject(target: HomeActivityViewModel)

    fun inject(target: NavigationDrawerFragmentViewModel)

    fun inject(target: NavigationDrawerItemViewModel)

    fun inject(target: HomeActivity)

    fun inject(target: HomeFragmentViewModel)

    fun inject(target: LoginActivityViewModel)

    fun inject(target: RegistrationFragmentViewModel)

    fun inject(target: ForgotPasswordFragmentViewModel)

}
