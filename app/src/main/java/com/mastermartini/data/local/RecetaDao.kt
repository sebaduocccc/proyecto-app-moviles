package com.mastermartini.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mastermartini.data.model.Receta
import kotlinx.coroutines.flow.Flow

@Dao
interface RecetaDao {

    @Insert
    suspend fun insertar(receta: Receta): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarTodas(recetas: List<Receta>)

    @Update
    suspend fun actualizar(receta: Receta)

    @Delete
    suspend fun eliminar(receta: Receta)

    @Query("SELECT * FROM recetas ORDER BY id")
    fun observarTodas(): Flow<List<Receta>>

    @Query("SELECT COUNT(*) FROM recetas")
    suspend fun contar(): Int

    @Query("SELECT * FROM recetas WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Receta?

    @Query("SELECT * FROM recetas WHERE id IN (:ids)")
    suspend fun buscarPorIds(ids: List<Int>): List<Receta>
}