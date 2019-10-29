package com.airasia.supportwheeloffate.ui.nav.adapter

import android.app.Application
import androidx.databinding.ObservableField
import com.airasia.supportwheeloffate.core.BaseViewModel
import com.airasia.supportwheeloffate.data.local.NavigationItem

class NavigationDrawerItemViewModel (app: Application): BaseViewModel(app){

    val title: ObservableField<String> = ObservableField()
    val iconRes: ObservableField<Int> = ObservableField()

    lateinit var navigationItem: NavigationItem

    fun setItem(item: NavigationItem) {
        this.navigationItem = item
        title.set(item.title)
        iconRes.set(item.menuIcon)
    }
}