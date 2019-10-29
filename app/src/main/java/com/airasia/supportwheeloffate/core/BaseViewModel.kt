package com.airasia.supportwheeloffate.core

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.airasia.supportwheeloffate.data.local.ViewType
import com.airasia.supportwheeloffate.utils.parseError
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    var compositeDisposable = CompositeDisposable()

    /**
     * Usage: remitApplication:mutableVisibility="@{viewModel.getLoadingVisibility()}"
     */
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    /**
     * Usage remitApplication:visibility="@{viewModel.emptyVisibility}"
     */
    val emptyVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val toastMessage: MutableLiveData<String> = MutableLiveData()
    val openView: MutableLiveData<ViewType?> = MutableLiveData()
    val errorDialog: MutableLiveData<String?> = MutableLiveData()

    val progressDialogMessage: MutableLiveData<String> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable?.isDisposed)
            compositeDisposable?.dispose()
    }

    private fun onEmptyData() {
        emptyVisibility.postValue(true)
    }

    fun onApiRequestError(it: Throwable?) {
        errorDialog.postValue(it?.parseError())
    }

    fun onApiRequestStart() {
        loadingVisibility.postValue(View.VISIBLE)
    }

    fun onApiRequestFinish() {
        loadingVisibility.postValue(View.GONE)
    }

    fun resetOpenView() {
        openView.postValue(null)
    }
}
