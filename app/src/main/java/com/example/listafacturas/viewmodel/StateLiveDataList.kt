package com.example.listafacturas.viewmodel

import androidx.lifecycle.MutableLiveData

class StateLiveDataList<T>: MutableLiveData<StateDataList<T>>() {
    fun setLoading() {
        value = StateDataList<T>().loading()
    }

    fun setNoData() {
        value = StateDataList<T>().noData()
    }

    fun setSuccess(data: T) {
        value = StateDataList<T>().success(data)
    }

    fun setFiltered(data: T) {
        value = StateDataList<T>().filtered(data)
    }
}