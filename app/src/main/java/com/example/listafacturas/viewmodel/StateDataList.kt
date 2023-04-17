package com.example.listafacturas.viewmodel

class StateDataList<T> {
    enum class DataState {
        CREATED,
        LOADING,
        NODATA,
        SUCCESS,
        ERROR
    }

    var state: DataState
    var data: T?

    init {
        this.state = DataState.CREATED
        this.data = null
    }

    fun loading(): StateDataList<T> {
        this.state = DataState.LOADING
        this.data = null
        return this
    }

    fun noData(): StateDataList<T> {
        this.state = DataState.NODATA
        this.data = null
        return this
    }

    fun success(data: T): StateDataList<T> {
        this.state = DataState.SUCCESS
        this.data = data
        return this
    }

}