package com.mastermartini.data.model

data class Receta(
    val id: Int,
    val title: String,
    val author: String,
    val imageRes: Int,

    val tiempoMin: Int, /*Tiempo en minutos*/
    val ingredientes: List<String>,
    val pasos: List<Paso>, /*Pasos de la receta*/

    val videoId: String? = null,
)