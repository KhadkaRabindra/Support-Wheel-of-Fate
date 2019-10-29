package com.airasia.supportwheeloffate.ui.nav

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.BaseViewModel
import com.airasia.supportwheeloffate.data.local.NavigationItem
import com.airasia.supportwheeloffate.repository.UserRepository
import javax.inject.Inject

class NavigationDrawerFragmentViewModel(app : Application) : BaseViewModel(app) {

    val userFullName: ObservableField<String> = ObservableField()
    val userName: ObservableField<String> = ObservableField()
    var navigationList: MutableLiveData<List<NavigationItem>> = MutableLiveData()

    @Inject
    lateinit var userRepository: UserRepository

    init {
        (app as? RemitApplication)?.component?.inject(this)
    }

    fun setNavigationList(navigationList: List<NavigationItem>){
        this.navigationList.postValue(navigationList)
    }

    fun setFullNameAndUserName(){
        this.userFullName.set(userRepository.getUserFullName())
        this.userName.set(userRepository.getUserID())
    }
}