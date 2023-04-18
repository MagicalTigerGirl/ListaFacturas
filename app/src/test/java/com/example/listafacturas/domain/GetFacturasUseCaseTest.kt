package com.example.listafacturas.domain

import android.content.Context
import android.support.test.InstrumentationRegistry
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.listafacturas.data.FacturaRepository
import com.example.listafacturas.data.FacturasDatabase
import com.example.listafacturas.data.model.Factura
import com.example.listafacturas.ui.application.MainApplication
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class GetFacturasUseCaseTest {

    @RelaxedMockK
    private lateinit var facturaRepository: FacturaRepository

    lateinit var getFacturasUseCase: GetFacturasUseCase
    lateinit var facturasDatabase: FacturasDatabase
    lateinit var appContext: Context

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        appContext = InstrumentationRegistry.getInstrumentation().targetContext

        facturasDatabase = Room.inMemoryDatabaseBuilder(
            appContext, FacturasDatabase::class.java
        ).allowMainThreadQueries().build()

        getFacturasUseCase = GetFacturasUseCase()
    }

    @Test
    fun apiReturnEmptyList() = runBlocking {
        //Given
        coEvery { facturaRepository.getAllFacturas() } returns emptyList()

        // When
        getFacturasUseCase()

        // Then
        coVerify(exactly = 1) { facturaRepository.getAllFacturasRoom() }
    }

    @Test
    fun apiReturnList() = runBlocking {
        //Given
        val list = listOf(Factura(26, "Pagada", 50.0, "26/12/2003"))
        coEvery { facturaRepository.getAllFacturas() } returns list

        // When
        val response = getFacturasUseCase()

        // Then
        coVerify(exactly = 1) { facturaRepository.deleteAllRoom() }
        coVerify(exactly = 1) { facturaRepository.insertAllRoom(any()) }
        coVerify(exactly = 0) { facturaRepository.getAllFacturasRoom() }
        assert(list == response)
    }
}