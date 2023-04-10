package com.example.listafacturas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listafacturas.data.model.Factura
import com.example.listafacturas.data.FacturaRepository
import kotlinx.coroutines.launch

class FacturaViewModel: ViewModel() {

    var liveDataList: StateLiveDataList<List<Factura>> = StateLiveDataList()

    var maxImporte: Double = 0.0

    fun getDataList() {

        viewModelScope.launch {
            liveDataList.setLoading()

            var list: List<Factura> = FacturaRepository.getAllFacturas()

            maxImporte = list.stream().max(Comparator.comparing(Factura::importeOrdenacion)).get().importeOrdenacion

            if (list.isEmpty())
                liveDataList.setNoData()
            else
                liveDataList.setSuccess(list)
        }
    }
}