package com.mastermartini.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mastermartini.data.model.Usuario

class UsuarioRepository {

    fun obtenerUsuariosDesdeAssets(context: Context, filename: String = "usuarios.json"): List<Usuario>{
        return try {
            val json = context.assets.open(filename).bufferedReader().use { it.readText() }
            val listType = object: TypeToken<List<Usuario>>() {}.type
            Gson().fromJson<List<Usuario>>(json, listType) ?: emptyList()
        } catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }


    }
}