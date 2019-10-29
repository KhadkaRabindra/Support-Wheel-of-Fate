package com.airasia.supportwheeloffate.ui.home.adapter

import android.app.Application
import androidx.databinding.ObservableField
import com.airasia.supportwheeloffate.core.BaseViewModel
import com.airasia.supportwheeloffate.data.local.HomeItem
import com.airasia.supportwheeloffate.data.local.Issue

class HomeAdapterViewModel (app: Application): BaseViewModel(app){

    var homeData : ObservableField<HomeItem> = ObservableField()
    val name: ObservableField<String> = ObservableField()
    val issues: ObservableField<ArrayList<Issue>> = ObservableField()

    fun setHomeData(item: HomeItem) {
        homeData.set(item)
        name.set(item.name)
        issues.set(item.issueList)
    }

}