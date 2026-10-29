package com.mastermartini.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mastermartini.data.model.Paso

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun listaStringAJson(lista: List<String>): String = gson.toJson(lista)

    @TypeConverter
    fun jsonAListaString(json: String): List<String> {
        val tipo = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(json, tipo) ?: emptyList()
    }

    @TypeConverter
    fun listaIntAJson(lista: List<Int>): String = gson.toJson(lista)

    @TypeConverter
    fun jsonAListaInt(json: String): List<Int> {
        val tipo = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson<List<Int>>(json, tipo) ?: emptyList()
    }

    @TypeConverter
    fun listaPasoAJson(lista: List<Paso>): String = gson.toJson(lista)

    @TypeConverter
    fun jsonAListaPaso(json: String): List<Paso> {
        val tipo = object : TypeToken<List<Paso>>() {}.type
        return gson.fromJson<List<Paso>>(json, tipo) ?: emptyList()
    }
}