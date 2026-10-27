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

    private val preferences = context.getSharedPreferences(
        "sesion_usuario",
        Context.MODE_PRIVATE
    )
    private val mutex = Mutex()

    override suspend fun login(email: String, password: String): Usuario? {
        cargarUsuariosIniciales()
        val usuario = dao.buscarPorEmail(email.trim()) ?: return null
        return usuario.takeIf { it.password == password }?.also {
            guardarUsuarioActual(it.id)}
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
        val id = dao.insertar(usuario).toInt()
        val usuarioRegistrado = usuario.copy(id = id)

        guardarUsuarioActual(usuarioRegistrado.id)
        return usuarioRegistrado
    }
    override suspend fun guardarUsuarioActual(usuarioId: Int) {
        preferences.edit()
            .putInt("usuario_id", usuarioId)
            .apply()
    }

    private suspend fun cargarUsuariosIniciales() = mutex.withLock {
        if (dao.contar() == 0) {
            dao.insertarTodos(obtenerUsuariosDesdeAssets())
        }
    }
    override suspend fun obtenerUsuarioActual(): Usuario? {
        cargarUsuariosIniciales()

        val usuarioId = preferences.getInt("usuario_id", -1)
        if (usuarioId == -1) return null

        return dao.buscarPorId(usuarioId)
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
    override suspend fun actualizarPerfil(
        nombre: String,
        apellido: String,
        username: String
    ): Usuario {
        val usuario = obtenerUsuarioActual()
            ?: throw IllegalStateException("No hay un usuario autenticado")

        val usernameLimpio = username.trim().removePrefix("@")

        val usernameEnUso = existeUsername(usernameLimpio)

        if (usernameEnUso && usernameLimpio != usuario.username) {
            throw IllegalArgumentException("Este nombre de usuario ya existe")
        }

        val actualizado = usuario.copy(
            nombre = nombre.trim(),
            apellido = apellido.trim(),
            username = usernameLimpio
        )

        dao.actualizar(actualizado)
        return actualizado
    }

}
