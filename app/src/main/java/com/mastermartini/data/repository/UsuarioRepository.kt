package com.mastermartini.data.repository

import com.mastermartini.data.model.Usuario

interface UsuarioRepository {

    suspend fun login(email: String, password: String): Usuario?

    suspend fun existeEmail(email: String): Boolean

    suspend fun existeUsername(username: String): Boolean

    suspend fun registrar(usuario: Usuario): Usuario

    suspend fun guardarUsuarioActual(usuarioId: Int)
    suspend fun obtenerUsuarioActual(): Usuario?
    suspend fun cerrarSesion()
    suspend fun actualizarPerfil(
        nombre: String,
        apellido: String,
        username: String
    ): Usuario
}
