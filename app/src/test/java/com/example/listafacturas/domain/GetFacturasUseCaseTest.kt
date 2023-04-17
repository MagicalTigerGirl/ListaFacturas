package com.example.listafacturas.domain

import com.example.listafacturas.data.FacturaRepository
import com.example.listafacturas.data.model.Factura
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.stub.StubRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class GetFacturasUseCaseTest {

    @RelaxedMockK
    private lateinit var facturaRepository: FacturaRepository

    lateinit var getFacturasUseCase: GetFacturasUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
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