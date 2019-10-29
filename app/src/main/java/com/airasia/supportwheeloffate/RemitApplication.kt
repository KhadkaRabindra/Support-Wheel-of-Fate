package com.airasia.supportwheeloffate

import androidx.multidex.MultiDexApplication
import com.airasia.supportwheeloffate.data.prefs.component.PreferenceComponent_UserInfoComponent
import com.airasia.supportwheeloffate.di.component.DaggerApplicationComponent
import com.airasia.supportwheeloffate.di.module.ApplicationModule
import com.airasia.supportwheeloffate.utils.Constants


class RemitApplication : MultiDexApplication() {

    var mLastPause: Long = 0
    private val APPLOCK_TIMEOUT = Constants.PasscodeTimeOut
    companion object {
        var isPinActivityVisible: Boolean = false

        fun pinActivityResumed() {
            isPinActivityVisible = true
        }

        fun pinActivityPaused() {
            isPinActivityVisible = false
        }
    }

    val component by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        PreferenceComponent_UserInfoComponent.init(this)
    }

}

