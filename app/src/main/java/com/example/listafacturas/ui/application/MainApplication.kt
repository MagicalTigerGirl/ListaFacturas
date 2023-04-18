package com.example.listafacturas.ui.application

import android.app.Application
import android.content.Context
import com.example.listafacturas.data.FacturasDatabase


class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FacturasDatabase.create(applicationContext)
    }
}