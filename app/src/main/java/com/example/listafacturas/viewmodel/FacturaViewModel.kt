package com.example.listafacturas.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listafacturas.data.FacturaRepository
import com.example.listafacturas.data.model.Factura
import kotlinx.coroutines.launch


class FacturaViewModel: ViewModel() {

    var liveDataList: StateLiveDataList<List<Factura>> = StateLiveDataList()

    init {
        viewModelScope.launch {
            val list: List<Factura> = FacturaRepository.getAllFacturas()
            FacturaRepository.deleteAll()
            FacturaRepository.insertListDatabase(list)
            if (!list.isEmpty())
                FacturaRepository.importeMaximo = list.stream().max(Comparator.comparing(Factura::importeOrdenacion)).get().importeOrdenacion+1

            importeMaxSelected = FacturaRepository.importeMaximo.toInt()
        }
    }

    fun getDataList() {

        viewModelScope.launch {
            liveDataList.setLoading()

            var list: List<Factura> = emptyList()

            if (!isFiltered)
                list = FacturaRepository.getList()
            else {
                when (result.value) {
                    FilterResult.IMPORTE ->  {
                        list = if (isChecked)
                            FacturaRepository.getListFilteredImporte(importeMaxSelected).filter { it.descEstado.equals("Pendiente de pago") && bPendientePagos.value == true || it.descEstado.equals("Pagada") && bPagadas.value == true }
                        else
                            FacturaRepository.getListFilteredImporte(importeMaxSelected)
                    }
                    FilterResult.IMPORTEHASTA ->  {
                        list = if (isChecked)
                            FacturaRepository.getListFilteredAllHasta(importeMaxSelected, fechaHasta).filter { it.descEstado.equals("Pendiente de pago") && bPendientePagos.value == true || it.descEstado.equals("Pagada") && bPagadas.value == true }
                        else
                            FacturaRepository.getListFilteredAllHasta(importeMaxSelected, fechaHasta)
                    }
                    FilterResult.ALL -> {
                        list = if (isChecked)
                            FacturaRepository.getListFilteredAll(importeMaxSelected, fechaDesde, fechaHasta).filter { it.descEstado.equals("Pendiente de pago") && bPendientePagos.value == true || it.descEstado.equals("Pagada") && bPagadas.value == true }
                        else
                            FacturaRepository.getListFilteredAll(importeMaxSelected, fechaDesde, fechaHasta)
                    }
                    else -> {}
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
    var fechaDesde: String = "día/mes/año"
    var fechaHasta: String = "día/mes/año"

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
        if (fechaDesde.equals("día/mes/año") && fechaHasta.equals("día/mes/año"))
            result.value = FilterResult.IMPORTE
        else if (fechaDesde.equals("día/mes/año") && !fechaHasta.equals("día/mes/año"))
            result.value = FilterResult.IMPORTEHASTA
        else
            result.value = FilterResult.ALL
    }
}