package com.mastermartini.data.repository

import com.mastermartini.data.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {

    val usuarios: Flow<List<Usuario>>

    suspend fun login(email: String, password: String): Usuario?

    suspend fun existeEmail(email: String): Boolean

    suspend fun existeUsername(username: String): Boolean

    suspend fun registrar(usuario: Usuario): Usuario

    suspend fun guardarUsuarioActual(usuarioId: Int)
    suspend fun obtenerUsuarioActual(): Usuario?
    suspend fun cerrarSesion()
    suspend fun obtenerPorId(id: Int): Usuario?
    suspend fun emailEnUso(email: String, excluirId: Int? = null): Boolean
    suspend fun usernameEnUso(username: String, excluirId: Int? = null): Boolean
    suspend fun crearUsuario(usuario: Usuario): Usuario
    suspend fun actualizarUsuario(usuario: Usuario)
    suspend fun eliminarUsuario(usuario: Usuario)
    suspend fun actualizarPerfil(
        nombre: String,
        apellido: String,
        username: String,
        imageUri: String?
    ): Usuario
}
