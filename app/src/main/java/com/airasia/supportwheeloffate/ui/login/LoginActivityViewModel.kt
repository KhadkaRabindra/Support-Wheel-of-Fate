package com.airasia.supportwheeloffate.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.eightsquarei.eremitpay.repository.ApiRepository
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.BaseViewModel
import com.airasia.supportwheeloffate.data.remote.LoginPostData
import com.airasia.supportwheeloffate.data.remote.LoginResponse
import com.airasia.supportwheeloffate.data.remote.User
import com.airasia.supportwheeloffate.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginActivityViewModel(app: Application) : BaseViewModel(app) {

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData()

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var userRepository: UserRepository

    init {
        (app as? RemitApplication)?.component?.inject(this)
    }

    fun getLoginStatus(): Boolean? {
        return userRepository.getLoginStatus()
    }

    fun requestLogin(deviceUniqueId: String) {
        val loginPostData = LoginPostData(
            userName = email.value?.trim().toString(),
            password = password.value?.trim().toString(),
            deviceIdentifier = deviceUniqueId
        )
        compositeDisposable.add(apiRepository.postLoginData(loginPostData)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onApiRequestStart() }
            .doOnTerminate { onApiRequestFinish() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //handleLoginStatus(response, deviceUniqueId)
                loginSuccess.postValue(true)
            }, {
                onApiRequestError(it)
            })
        )
    }

    /*fun checkPasscodeExist() {
        compositeDisposable.add(apiRepository.passcodeExist()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onApiRequestStart() }
            .doOnTerminate { onApiRequestFinish() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                userRepository.putLoginStatus(true)
                userRepository.putHasPasscode(response?.isAvailable == true)
                passCodeCheckSuccess.postValue(true)
            }, {
                onApiRequestError(it)
            })
        )
    }*/

    private fun handleLoginStatus(loginResponse: LoginResponse?, deviceUniqueId: String) {
        userRepository.putUserDetail(
            User(
                deviceKey = loginResponse?.deviceKey,
                refreshToken = loginResponse?.refreshToken,
                name = loginResponse?.name,
                phoneNumber = loginResponse?.phoneNumber,
                emailAddress = loginResponse?.emailAddress,
                userDetail = loginResponse?.userDetail,
                deviceIdentifier = deviceUniqueId
                //userID = username.value?.trim().toString()
            )
        )
        loginSuccess.postValue(true)
    }


}