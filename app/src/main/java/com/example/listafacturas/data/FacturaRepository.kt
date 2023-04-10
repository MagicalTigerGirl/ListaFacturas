package com.example.listafacturas.data

import com.example.listafacturas.data.model.Factura
import com.example.listafacturas.core.FacturaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FacturaRepository {
    suspend fun getAllFacturas(): List<Factura> {
        return withContext(Dispatchers.IO) {
            FacturaClient.service.listFacturas().body()?.facturas ?: emptyList()
        }
    }
}