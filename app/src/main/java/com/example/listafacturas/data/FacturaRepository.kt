package com.example.listafacturas.data

import com.example.listafacturas.core.FacturaClient
import com.example.listafacturas.data.dao.FacturaDao
import com.example.listafacturas.data.model.Factura
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FacturaRepository {

    private var facturaDao: FacturaDao? = null
    var importeMaximo: Double = 0.0
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        facturaDao = FacturasDatabase.dataBase?.facturaDao()
    }

    suspend fun getAllFacturas(): List<Factura> {
        return withContext(dispatcher) {
            val result = FacturaClient.service.listFacturas().body()?.facturas ?: emptyList()

            result
        }
    }

    suspend fun insertListDatabase(list: List<Factura>) {
        withContext(dispatcher) {
            FacturasDatabase.dataBase!!.facturaDao()?.insert(list)
        }
    }

    suspend fun deleteAll() {
        withContext(dispatcher) {
            FacturasDatabase.dataBase!!.facturaDao()?.deleteAll()
        }
    }

    suspend fun getList(): List<Factura> {
        return withContext(dispatcher) {
            val list: List<Factura>? = FacturasDatabase.dataBase!!.facturaDao()?.select()
            list?: emptyList()
        }
    }

    suspend fun getListFilteredAll(importe: Int, desde: String, hasta: String): List<Factura> {
        return withContext(dispatcher) {
            val list: List<Factura>? = FacturasDatabase.dataBase!!.facturaDao()?.selectFilteredAll(importe, desde, hasta)
            list?: emptyList()
        }
    }

    suspend fun getListFilteredAllHasta(importe: Int, hasta: String): List<Factura> {
        return withContext(dispatcher) {
            val list: List<Factura>? = FacturasDatabase.dataBase!!.facturaDao()?.selectFilteredAllHasta(importe, hasta)
            list?: emptyList()
        }
    }

    suspend fun getListFilteredImporte(importe: Int): List<Factura> {
        return withContext(dispatcher) {
            val list: List<Factura>? = FacturasDatabase.dataBase!!.facturaDao()?.selectFilteredImporte(importe)
            list?: emptyList()
        }
    }
}