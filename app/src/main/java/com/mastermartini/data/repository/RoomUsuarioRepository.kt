package com.mastermartini.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mastermartini.data.local.UsuarioDao
import com.mastermartini.data.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class RoomUsuarioRepository(
    private val context: Context,
    private val dao: UsuarioDao
) : UsuarioRepository {

    private val mutex = Mutex()

    override suspend fun login(email: String, password: String): Usuario? {
        cargarUsuariosIniciales()
        val usuario = dao.buscarPorEmail(email.trim()) ?: return null
        return usuario.takeIf { it.password == password }
    }

    override suspend fun existeEmail(email: String): Boolean {
        cargarUsuariosIniciales()
        return dao.buscarPorEmail(email.trim()) != null
    }

    override suspend fun existeUsername(username: String): Boolean {
        cargarUsuariosIniciales()
        return dao.buscarPorUsername(username.trim()) != null
    }

    override suspend fun registrar(usuario: Usuario): Usuario {
        cargarUsuariosIniciales()
        val id = dao.insertar(usuario)
        return usuario.copy(id = id.toInt())
    }

    private suspend fun cargarUsuariosIniciales() = mutex.withLock {
        if (dao.contar() == 0) {
            dao.insertarTodos(obtenerUsuariosDesdeAssets())
        }
    }

    private suspend fun obtenerUsuariosDesdeAssets(filename: String = "usuarios.json"): List<Usuario> =
        withContext(Dispatchers.IO) {
            try {
                val json = context.assets.open(filename).bufferedReader().use { it.readText() }
                val listType = object : TypeToken<List<Usuario>>() {}.type
                Gson().fromJson<List<Usuario>>(json, listType) ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
}
