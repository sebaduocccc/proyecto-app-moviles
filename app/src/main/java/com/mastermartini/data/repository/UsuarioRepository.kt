package com.mastermartini.data.repository

import com.mastermartini.data.model.Usuario

interface UsuarioRepository {

    suspend fun login(email: String, password: String): Usuario?

    suspend fun existeEmail(email: String): Boolean

    suspend fun existeUsername(username: String): Boolean

    suspend fun registrar(usuario: Usuario): Usuario
}
