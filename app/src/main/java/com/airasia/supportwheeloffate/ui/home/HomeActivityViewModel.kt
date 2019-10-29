package com.airasia.supportwheeloffate.ui.home

import android.app.Application
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.BaseViewModel

class HomeActivityViewModel(app : Application) : BaseViewModel(app){
    init {
        (app as? RemitApplication)?.component?.inject(this)
    }

}