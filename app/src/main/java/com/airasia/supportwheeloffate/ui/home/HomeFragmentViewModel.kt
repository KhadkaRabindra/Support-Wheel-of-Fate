package com.airasia.supportwheeloffate.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.eightsquarei.eremitpay.repository.ApiRepository
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.BaseViewModel
import com.airasia.supportwheeloffate.data.local.HomeItem
import javax.inject.Inject

class HomeFragmentViewModel (app : Application) : BaseViewModel(app){

    @Inject
    lateinit var apiRepository: ApiRepository

    init {
        (app as? RemitApplication)?.component?.inject(this)
    }

    var homeDataList: MutableLiveData<List<HomeItem?>?> = MutableLiveData()

    fun setHomeData(homeDataList: List<HomeItem?>?) {
        this.homeDataList.postValue(homeDataList)
    }

}