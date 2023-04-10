package com.example.listafacturas.data.network

import com.example.listafacturas.data.model.FacturaResult
import retrofit2.Response
import retrofit2.http.GET

interface FacturaService {
    @GET("facturas")
    suspend fun listFacturas(): Response<FacturaResult>
}