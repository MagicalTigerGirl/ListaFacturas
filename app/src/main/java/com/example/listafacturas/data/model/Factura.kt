package com.example.listafacturas.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "factura")
data class Factura (
    @PrimaryKey(autoGenerate = true) @NonNull val id: Int,
    val descEstado: String,
    val importeOrdenacion: Double,
    val fecha: String
)