package com.mastermartini.di

import android.content.Context
import androidx.room.Room
import com.mastermartini.data.local.AppDatabase
import com.mastermartini.data.local.ImageStorage
import com.mastermartini.data.repository.ProductoRepository
import com.mastermartini.data.repository.RecetaRepository
import com.mastermartini.data.repository.RoomProductoRepository
import com.mastermartini.data.repository.RoomRecetaRepository
import com.mastermartini.data.repository.RoomUsuarioRepository
import com.mastermartini.data.repository.UsuarioRepository

class AppContainer(context: Context) {

    private val database: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "master_martini.db")
            .fallbackToDestructiveMigration(true).build()

    private val imageStorage = ImageStorage(context)

    val recetaRepository: RecetaRepository =
        RoomRecetaRepository(context, database.recetaDao(), imageStorage)
    val productoRepository: ProductoRepository =
        RoomProductoRepository(context, database.productoDao(), imageStorage)
    val usuarioRepository: UsuarioRepository =
        RoomUsuarioRepository(context, database.usuarioDao())
}
