package com.airasia.supportwheeloffate.core

import android.app.Application
import com.airasia.supportwheeloffate.RemitApplication

class ToolbarCommonActivityViewModel(app: Application) : BaseViewModel(app) {

    init {
        (app as? RemitApplication)?.component?.inject(this)
    }

}