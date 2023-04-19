package com.example.listafacturas.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listafacturas.data.FacturaRepository
import com.example.listafacturas.data.model.Factura
import com.example.listafacturas.domain.GetFacturasUseCase
import kotlinx.coroutines.launch


class FacturaViewModel: ViewModel() {

    var liveDataList: StateLiveDataList<List<Factura>> = StateLiveDataList()

    val dateInit: String = "día/mes/año"
    private var getFacturasUseCase = GetFacturasUseCase()

    var list: List<Factura> = emptyList()

    init {
        viewModelScope.launch {
            list = getFacturasUseCase()
            if (list.isEmpty())
                liveDataList.setNoData()
            else {
                liveDataList.setSuccess(list)
                importeMaxSelected = FacturaRepository.importeMaximo.toInt()
            }
        }
    }

    fun getDataList() {
        liveDataList.setLoading()

        viewModelScope.launch {
            if (isFiltered) {
                val filteredList: List<Factura>
                when (result.value) {
                    FilterResult.IMPORTE ->  {
                        filteredList = FacturaRepository.getListFilteredImporte(importeMaxSelected)
                    }
                    FilterResult.IMPORTEHASTA ->  {
                        filteredList = FacturaRepository.getListFilteredAllHasta(importeMaxSelected, fechaHasta)
                    }
                    FilterResult.ALL -> {
                        filteredList = FacturaRepository.getListFilteredAll(importeMaxSelected, fechaDesde, fechaHasta)
                    }
                    else -> {
                        filteredList = emptyList()
                    }
                }
                list = if (isChecked) {
                    filteredList.filter { it.descEstado.equals("Pendiente de pago") && bPendientePagos.value == true || it.descEstado.equals("Pagada") && bPagadas.value == true }
                } else {
                    filteredList
                }
            }

            if (list.isEmpty())
                liveDataList.setNoData()
            else
                liveDataList.setSuccess(list)
        }
    }

    // SECONDFRAGMENT

    var isFiltered = false

    // Fechas DatePicker
    var fechaDesde: String = dateInit
    var fechaHasta: String = dateInit

    // Seekbar
    var importeMaxSelected: Int = 0

    var isChecked:  Boolean = false

    // Checkbox
    var bPagadas: MutableLiveData<Boolean> = MutableLiveData()
    var bAnuladas: MutableLiveData<Boolean> = MutableLiveData()
    var bCuotaFija: MutableLiveData<Boolean> = MutableLiveData()
    var bPendientePagos: MutableLiveData<Boolean> = MutableLiveData()
    var bPlanPago: MutableLiveData<Boolean> = MutableLiveData()

    var result: MutableLiveData<FilterResult> = MutableLiveData()

    fun filter() {
        if (fechaDesde.equals(dateInit) && fechaHasta.equals(dateInit))
            result.value = FilterResult.IMPORTE
        else if (fechaDesde.equals(dateInit) && !fechaHasta.equals(dateInit))
            result.value = FilterResult.IMPORTEHASTA
        else
            result.value = FilterResult.ALL
    }
}