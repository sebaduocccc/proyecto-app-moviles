package com.mastermartini.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class Receta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val imageUri: String?,

    val tiempoMin: Int, /*Tiempo en minutos*/
    val ingredientes: List<String>,
    val pasos: List<Paso>, /*Pasos de la receta*/

    val videoId: String? = null,
)