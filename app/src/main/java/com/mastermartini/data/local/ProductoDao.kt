package com.mastermartini.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mastermartini.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Insert
    suspend fun insertar(producto: Producto): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarTodos(productos: List<Producto>)

    @Update
    suspend fun actualizar(producto: Producto)

    @Delete
    suspend fun eliminar(producto: Producto)

    @Query("SELECT * FROM productos ORDER BY id")
    fun observarTodos(): Flow<List<Producto>>

    @Query("SELECT COUNT(*) FROM productos")
    suspend fun contar(): Int

    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Producto?
}