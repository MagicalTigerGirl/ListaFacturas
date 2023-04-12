package com.example.listafacturas.ui.application

import android.app.Application
import android.content.Context
import com.example.listafacturas.data.FacturasDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FacturasDatabase.create(applicationContext)
    }
}