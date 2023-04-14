package com.example.listafacturas.core

import com.example.listafacturas.data.network.FacturaService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FacturaClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://viewnextandroid.wiremockapi.cloud/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(FacturaService::class.java)
}