package com.mastermartini.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mastermartini.data.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarTodos(usuarios: List<Usuario>)

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun contar(): Int

    @Query("SELECT * FROM usuarios WHERE LOWER(email) = LOWER(:email) LIMIT 1")
    suspend fun buscarPorEmail(email: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE LOWER(username) = LOWER(:username) LIMIT 1")
    suspend fun buscarPorUsername(username: String): Usuario?
}
