package com.market.admin.mvvm

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.market.admin.model.ResponseModel
import com.market.admin.network.NetworkUtil.isHttpStatusCode
import com.market.admin.network.RestClient
import com.market.admin.utils.log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class VerifyViewModel : ViewModel() {

    val requestUpdateData = MutableLiveData<ResponseModel>()
    val errorMess = MutableLiveData<String>()


    @SuppressLint("CheckResult")
    fun getVenderListAPI(){
        RestClient.webServices().getVenderList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                requestUpdateData.value = it!!
            },
                {
                    val mess = isHttpStatusCode(it)
                    log("VenderListAPI", "Error=>$mess")
                    errorMess.value = mess
                }
            )


    }
}