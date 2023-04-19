package com.example.listafacturas.domain

import android.support.test.runner.AndroidJUnit4
import com.example.listafacturas.data.FacturaRepository
import com.example.listafacturas.data.model.Factura
import org.junit.runner.RunWith

class GetFacturasUseCase {

    suspend operator fun invoke(): List<Factura> {

        val list = FacturaRepository.getAllFacturas()

        return if(list.isNotEmpty()) {
            FacturaRepository.deleteAllRoom()
            FacturaRepository.insertAllRoom(list)
            FacturaRepository.importeMaximo = list.stream().max(Comparator.comparing(Factura::importeOrdenacion)).get().importeOrdenacion+1

            list
        } else {
            FacturaRepository.getAllFacturasRoom()
        }
    }
}