package com.airasia.supportwheeloffate.ui.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.eightsquarei.eremitpay.repository.ApiRepository
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.BaseViewModel
import com.airasia.supportwheeloffate.data.local.Postcode
import com.airasia.supportwheeloffate.data.remote.RegistrationPostData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegistrationFragmentViewModel (app: Application) : BaseViewModel(app) {

    val selectedPostcode: MutableLiveData<Postcode> = MutableLiveData()
    var fullName: MutableLiveData<String> = MutableLiveData()
    var mobileNumber : MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password : MutableLiveData<String> = MutableLiveData()
    var confirmPassword : MutableLiveData<String> = MutableLiveData()

    /*var mobileNumber: MutableLiveData<String> = MutableLiveData()
    var postalCode: MutableLiveData<String> = MutableLiveData()
    var state: MutableLiveData<String> = MutableLiveData()
    var city: MutableLiveData<String> = MutableLiveData()
    var registeredAddress: MutableLiveData<String> = MutableLiveData()
    var remarks: MutableLiveData<String> = MutableLiveData()*/
    var isRegistrationSucces: MutableLiveData<Boolean> = MutableLiveData()
    //for api response

    @Inject
    lateinit var apiRepository: ApiRepository

    init {
        (app as? RemitApplication)?.component?.inject(this)
    }

    fun requestRegistrationSubmit() {
        val registrationPostData = RegistrationPostData(
            fullName = fullName.value.toString(),
            mobileNumber = mobileNumber.value.toString(),
            email = email.value.toString(),
            password = password.value.toString(),
            confirmPassword = confirmPassword.value.toString()
        )
        compositeDisposable.add(apiRepository.postRegistrationData(registrationPostData)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onApiRequestStart() }
            .doOnTerminate { onApiRequestFinish() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isRegistrationSucces.postValue(true)
            }, {
                onApiRequestError(it)
            })
        )
    }

}