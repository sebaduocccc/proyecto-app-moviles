package com.mastermartini.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mastermartini.data.model.Producto
import com.mastermartini.data.model.Usuario
import com.mastermartini.data.model.Receta

@Database(entities = [Usuario::class, Receta::class, Producto::class], version = 3, exportSchema = false)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun recetaDao(): RecetaDao
    abstract fun productoDao(): ProductoDao
}
