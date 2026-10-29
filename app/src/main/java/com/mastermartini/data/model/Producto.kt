package com.mastermartini.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Int,
    val imageUri: String?,

    val recetaIds: List<Int> = emptyList(),
)