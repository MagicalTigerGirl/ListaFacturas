package com.example.listafacturas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listafacturas.data.model.Factura
import com.example.listafacturas.data.FacturaRepository
import kotlinx.coroutines.launch

class FacturaViewModel: ViewModel() {

    var liveDataList: StateLiveDataList<List<Factura>> = StateLiveDataList()

    init {
        viewModelScope.launch {
            val list: List<Factura> = FacturaRepository.getAllFacturas()
            FacturaRepository.deleteAll()
            FacturaRepository.insertListDatabase(list)
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
                if (fechaDesde.equals("día/mes/año") && fechaHasta.equals("día/mes/año"))
                    list = FacturaRepository.getListFilteredImporte(importeMaxSelected).filter { it.descEstado.equals("Pendiente de pago") && bPendientePagos || it.descEstado.equals("Pagada") && bPagadas }
                else if (fechaDesde.equals("día/mes/año") && !fechaHasta.equals("día/mes/año") )
                    list = FacturaRepository.getListFilteredAllHasta(importeMaxSelected, fechaHasta).filter { it.descEstado.equals("Pendiente de pago") && bPendientePagos || it.descEstado.equals("Pagada") && bPagadas }
                else
                    list = FacturaRepository.getListFilteredAll(importeMaxSelected, fechaDesde, fechaHasta).filter { it.descEstado.equals("Pendiente de pago") && bPendientePagos || it.descEstado.equals("Pagada") && bPagadas }
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

    // Checkbox
    var bPagadas = false
    var bAnuladas = false
    var bCuotaFija = false
    var bPendientePagos = false
    var bPlanPago = false
}